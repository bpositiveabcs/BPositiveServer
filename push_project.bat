@echo off
setlocal enabledelayedexpansion

echo Starting update check for main project and all submodules...

:: Ensure we start in the main project directory
cd %~dp0

:: Fetch and check for updates in the main project
echo Checking for updates in the main project...
git fetch origin
git rev-parse @ > %temp%\current_head
git rev-parse @{u} > %temp%\upstream_head
fc %temp%\current_head %temp%\upstream_head > nul
if errorlevel 1 (
    echo Updates are available for the main project.
    set /p pull="Do you want to pull the latest changes for the main project (y/n)? "
    if /I "!pull!"=="y" (
        git pull origin main
        if errorlevel 1 (
            echo Failed to pull updates for the main project.
            exit /b %errorlevel%
        )
    )
) else (
    echo No updates are available for the main project.
)

:: Update each submodule
echo Checking and updating submodules...
git submodule foreach --recursive "git fetch origin && git rev-parse HEAD > %temp%\current_sub_head && git rev-parse @{u} > %temp%\upstream_sub_head && fc %temp%\current_sub_head %temp%\upstream_sub_head > nul && if errorlevel 1 (echo Updates available for %path% && set /p pull_sub=Do you want to pull the latest changes for %path% (y/n)? && if /I "!pull_sub!"=="y" (git pull origin HEAD && if errorlevel 1 (echo Failed to pull updates for %path%. && exit /b %errorlevel%))))"
if errorlevel 1 (
    echo Failed to process one or more submodules.
    exit /b %errorlevel%
)

:: Commit any changes, if they exist
git commit -am "Updated main project and submodules to the latest versions"
if errorlevel 1 (
    echo No new changes to commit.
)

:: Push all changes
echo Attempting to push updates to remote repository...
git push
if errorlevel 1 (
    echo Failed to push the updates to the remote repository.
    exit /b %errorlevel%
)

echo Update and push completed successfully!
endlocal
