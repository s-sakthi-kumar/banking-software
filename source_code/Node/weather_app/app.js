
const express = require('express')
const app = express()


url = 'https://api.open-meteo.com/v1/forecast?latitude=12.9719&longitude=77.5937&daily=weather_code&hourly=temperature_2m'
newURL = 'https://api.open-meteo.com/v1/forecast?latitude=12.9719&longitude=77.5937&daily=weather_code,uv_index_max&current=apparent_temperature,is_day,temperature_2m'

async function getWeather(){

try {
        // Fetching data from a third-party URL
        const response = await fetch(newURL);
        const data = await response.json();

        // Forwarding that data as your own server's response
      return data; 
    } catch (error) {
        res.status(500).json({ error: 'Failed to fetch external data' });
    }

}


app.get('/weather', async (req, res) => {
   const weatherData = await getWeather(); // async call
   res.json(weatherData); // send JSON response
});


app.listen(3000, (req, res) => {
   console.log("App is running on port 3000")
})

