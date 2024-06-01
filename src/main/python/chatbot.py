from flask import Flask, request, jsonify

app = Flask(__name__)

predefined_responses = {
    "Why should I donate blood?": "Donating blood saves lives. It helps patients undergoing surgery, recovering from accidents, or dealing with certain medical conditions like anemia or cancer.",
    "Who can donate blood?": "Most people who are in good health, weigh at least 110 pounds, and are 17 years of age or older (in most states) can donate blood. There may be additional eligibility criteria depending on the blood donation center and local regulations.",
    "How often can I donate blood?": "You can typically donate whole blood every 56 days, or about every two months. However, the frequency may vary depending on the type of donation (whole blood, platelets, plasma) and your health status.",
    "Is it safe to donate blood?": "Yes, donating blood is safe. Blood donation centers adhere to strict protocols to ensure the safety of donors and recipients. All equipment used is sterile and disposable, and trained staff members oversee the donation process.",
    "Does donating blood have any health benefits for the donor?": "While the primary motivation for donating blood should be altruistic, some studies suggest that regular blood donation may have health benefits for the donor, such as reducing the risk of certain diseases like heart disease and cancer.",
    "What should I do before donating blood?": "Before donating blood, make sure you're well-hydrated and have eaten a nutritious meal. Avoid fatty foods just before donation. Be prepared to provide information about your medical history and current health status.",
    "Are there any side effects of donating blood?": "Most people experience no side effects or only minor ones, such as dizziness or lightheadedness, which typically resolve quickly. Serious side effects are rare but can include allergic reactions or fainting.",
    "Can I donate blood if I have a tattoo or piercing?": "In many cases, having a tattoo or piercing does not automatically disqualify you from donating blood. However, there may be specific waiting periods depending on when you got the tattoo or piercing, and it's essential to follow the guidelines provided by the blood donation center.",
    "What are the different types of blood donations?": "There are several types of blood donations, including whole blood donation, platelet donation, and plasma donation. Each type serves different medical purposes.",
    "How long does the blood donation process take?": "The time it takes to donate blood varies depending on the type of donation and the individual's physiology. Typically, a whole blood donation takes about 8-10 minutes, while platelet or plasma donations may take longer.",
    "Can I donate blood if I have a cold or the flu?": "If you have a cold or the flu, it's best to wait until you've fully recovered before donating blood. Donating blood when you're sick can put both you and the recipient at risk.",
    "Is there a minimum age requirement for blood donation?": "Yes, in most places, you must be at least 17 years old to donate blood. Some states may allow 16-year-olds to donate with parental consent. There may also be upper age limits, but they vary by location.",
    "Can I donate blood if I'm pregnant or breastfeeding?": "If you're pregnant or breastfeeding, it's generally recommended to wait until after childbirth or weaning before donating blood. Pregnancy and breastfeeding can affect iron levels and overall health.",
    "What happens to donated blood after it's collected?": "After donation, blood is tested for compatibility, screened for infectious diseases, and processed into various blood components (red blood cells, platelets, plasma) as needed. It's then stored and distributed to hospitals and medical centers for transfusion.",
    "Can I donate blood if I have a chronic medical condition?": "Whether you can donate blood with a chronic medical condition depends on the condition and its severity. Some conditions may disqualify you from donating, while others may not. It's best to consult with a healthcare professional or the blood donation center.",
    "What should I do after donating blood?": "After donating blood, it's essential to rest for a short time and drink plenty of fluids to help replenish your body's fluids. Avoid strenuous physical activity for the remainder of the day and follow any specific post-donation instructions provided by the blood donation center."
}

@app.route("/chatbot", methods=["POST"])
def chatbot():
    data = request.json
    user_message = data["message"]

    if user_message in predefined_responses:
        response = predefined_responses[user_message]
    else:
        response = "Sorry, I don't understand that question."

    return jsonify({"response": response})

if __name__ == "__main__":
    print("Starting chatbot server...")
    app.run(host="0.0.0.0", port=5000)
