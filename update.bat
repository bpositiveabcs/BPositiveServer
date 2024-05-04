@echo off
setlocal enabledelayedexpansion

echo Updating main project and submodule...

echo Checking for main project updates...
git fetch origin

:: Check if the local branch is behind the remote branch
git rev-parse @ > %temp%\current_head
git rev-parse @{u} > %temp%\upstream_head
fc %temp%\current_head %temp%\upstream_head > nul

if errorlevel 1 (
    echo New updates are available for the main project.
    set /p pull="Do you want to pull the latest changes (y/n)? "
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

echo Updating submodule...
cd BPositiveCommon
if errorlevel 1 (
    echo Failed to change directory to the submodule.
    exit /b %errorlevel%
)

git fetch origin

:: Check if the submodule is behind
git rev-parse @ > %temp%\current_sub_head
git rev-parse @{u} > %temp%\upstream_sub_head
fc %temp%\current_sub_head %temp%\upstream_sub_head > nul

if errorlevel 1 (
    echo New updates are available for the submodule.
    set /p pull_sub="Do you want to pull the latest submodule changes (y/n)? "
    if /I "!pull_sub!"=="y" (
        git checkout main
        git pull origin main
        if errorlevel 1 (
            echo Failed to pull updates for the submodule.
            exit /b %errorlevel%
        )
    )
) else (
    echo No updates are available for the submodule.
)

cd ..
if errorlevel 1 (
    echo Failed to change back to the main project directory.
    exit /b %errorlevel%
)

git add BPositiveCommon
if errorlevel 1 (
    echo Failed to add submodule changes.
    exit /b %errorlevel%
)

git commit -m "Updated BPositiveCommon submodule to the latest version"
if errorlevel 1 (
    echo No new changes to commit for the submodule.
)

git push
if errorlevel 1 (
    echo Failed to push the updates to remote repository.
    exit /b %errorlevel%
)

echo Update completed successfully!
endlocal
