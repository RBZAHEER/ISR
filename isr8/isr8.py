import requests

def get_weather(city_name, api_key):
    base_url = "http://api.openweathermap.org/data/2.5/weather?"
    complete_url = base_url + "q=" + city_name + "&appid=" + api_key + "&units=metric"
    response = requests.get(complete_url)
    weather_data = response.json()
    
    print(weather_data)  # You can keep this line for debugging purposes

    if weather_data["cod"] == 200:
        main_info = weather_data["main"]
        wind_info = weather_data["wind"]
        weather_info = weather_data["weather"][0]
        
        temperature = main_info["temp"]
        wind_speed = wind_info["speed"]
        description = weather_info["description"]
        weather_main = weather_info["main"]
        
        print(f"City: {city_name}")
        print(f"Temperature: {temperature}°C")
        print(f"Wind Speed: {wind_speed} m/s")
        print(f"Weather Description: {description}")
        print(f"Weather: {weather_main}")
    else:
        print(f"Error: {weather_data['message']}")

if __name__ == "__main__":
    api_key = "bb0aac11b3f7b7e1d4e23567ed0b01b4"  # Make sure to keep your API key secure
    city_name = input("Enter city name: ")
    get_weather(city_name, api_key)
