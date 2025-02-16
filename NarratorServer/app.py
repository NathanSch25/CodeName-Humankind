#Libraries
import os
import json as js
import requests
import re
from openai import OpenAI

from flask import Flask, request, jsonify, render_template
from dotenv import load_dotenv

#Load .Env
load_dotenv()

#Access Env Variables
api_key = os.getenv("OPEN_API_KEY")

#Dialogue Memory Storage (We can database this)
Memory = {}

#Create App
app = Flask(__name__)
client = OpenAI(api_key=api_key, base_url="https://api.perplexity.ai")

#--|| App Routes
@app.route('/') #Default Route (No Params)
def index():
    return render_template("index.html")

@app.route("/generate_dialogue", methods=['POST'])
def generate_dialogue():
    #Parse Data
    data = request.json #Fetch Send Data
    game_id = data.get('game_id') #Get Game Identifier
    turn_index = data.get('currentTurn') #Get Turn Index
    entities = data.get('entities',[]) #Get entities
    map = data.get('map') #Get map
    
    #We can modify these prompts based on acts
    prompt = f"""
    Generate a dialogue sequence for a game turn. Game ID: {game_id}, Turn: {turn_index}. Entities and their information: {js.dumps(entities)}. World Information:{map},
    The game takes tackles the idea that we shouldn't reply too much on technology. "Player" is fighting against technology but has the options of slowly becoming
    what he fights, as seen through allignment. You're aiming for context sensitive information and impactful|emotional dialogue. Allies are against anything low alignment
    and any X characer has a visible range of about 2-3 steps use position as index, and map to gauge distance. Keep this in mind with dialogue (not all agents need to speak).
     Entities informatio informs you what action the entity has just taken before/as this dialogue is generated.
    """
    
    #Post Request To LLM
    try:
        response = client.chat.completions.create(
            model= "sonar-pro",
            messages = [
                {
                    "role": "system", 
                    "content": (f"""
                                Generate a sequence of dialogues for one or multiple entities in JSON format., only the json output is required
                                Follow the json format
                                [(braces)
                                    Entity: Message,
                                    ...
                                (braces)], and be sure to reference memory for a better logic flow {Memory}, and the entities change in stats. Also alignment is a hidden stat though visual elements are shown at drastic values (cybernetics)
                                """
                    ),
                },  
                {
                    "role": "user",
                    "content": (prompt),},
                ]   
        )
                # Ensure the response contains the expected structure
        if response and hasattr(response, "choices") and response.choices:
            dialogue_sequence = response.choices[0].message.content  # Extract dialogue text
            # Remove the triple backticks and json identifier
            json_str = re.sub(r"```json|```", "", dialogue_sequence).strip()

            # Parse JSON
            data = js.loads(json_str)
            
            print(data)
            
            # Store the dialogue sequence
            if game_id not in Memory:
                Memory[game_id] = {}
            Memory[game_id][turn_index] = dialogue_sequence
            return jsonify({'dialogue_sequence: ':dialogue_sequence})
        else:
            print("Error: Unexpected response structure")
    except Exception as e:
        print(f"An error occurred: {e}")
    
@app.route('/get_game_dialogue', methods=['GET'])
def get_game_dialogue():
    game_id = request.args.get('game_id')
    if game_id in Memory:
        return jsonify(Memory[game_id])
    else:
        return jsonify({"error": "Game not found"}), 404

if __name__ == '__main__':
    app.run(debug=True)
                                


