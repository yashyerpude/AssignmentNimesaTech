package ApiWeatherTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WeatherApi 
{


	public class WeatherForecast {
	    private static final String API_KEY = "\r\n"
	    		+ "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
	    private static final String API_BASE_URL = "https://api.weatherapi.com/v1/forecast.json?key=" + API_KEY + "&q=London&days=1";

	    private Map<LocalDate, WeatherData> weatherDataMap;

	    @BeforeClass
	    public void setup() {
	        weatherDataMap = new HashMap<LocalDate, WeatherData>();
	        fetchWeatherData();
	    }

	    @AfterMethod
	    public void resetConsole() {
	        System.out.println("\n------------------------------\n");
	    }

	    @Test
	    public void testWeatherForecast() {
	        Scanner scanner = new Scanner(System.in);
	        int option;

	        do {
	            printMenu();
	            option = scanner.nextInt();
	            scanner.nextLine(); 

	            switch (option) {
	                case 1:
	                    getTemperature(scanner);
	                    break;
	                case 2:
	                    getWindSpeed(scanner);
	                    break;
	                case 3:
	                    getPressure(scanner);
	                    break;
	                case 0:
	                    System.out.println("Exiting the program...");
	                    break;
	                default:
	                    System.out.println("Invalid option! Please try again.");
	            }
	        } while (option != 0);
	    }

	    private void printMenu() {
	        System.out.println("Menu:");
	        System.out.println("1. Get weather");
	        System.out.println("2. Get Wind Speed");
	        System.out.println("3. Get Pressure");
	        System.out.println("0. Exit");
	        System.out.print("Enter your option: ");
	    }

	    private void getTemperature(Scanner scanner) {
	        System.out.print("Enter the date (YYYY-MM-DD): ");
	        String date = scanner.nextLine();

	        LocalDate selectedDate = LocalDate.parse(date);
	        WeatherData weatherData = weatherDataMap.get(selectedDate);
	        if (weatherData != null) {
	            System.out.println("Temperature for " + selectedDate + ": " + weatherData.getTemperature() + "°C");
	        } else {
	            System.out.println("No weather data available for " + selectedDate);
	        }
	    }

	    private void getWindSpeed(Scanner scanner) {
	        System.out.print("Enter the date (YYYY-MM-DD): "); // WE HAVE TO ENTER SPECIFIC DATE
	        String date = scanner.nextLine();

	        LocalDate selectedDate = LocalDate.parse(date);
	        WeatherData weatherData = weatherDataMap.get(selectedDate);
	        if (weatherData != null) {
	            System.out.println("Wind Speed for " + selectedDate + ": " + weatherData.getWindSpeed() + " km/h");
	        } else {
	            System.out.println("No weather data available for " + selectedDate);
	        }
	    }

	    private void getPressure(Scanner scanner) {
	        System.out.print("Enter the date (YYYY-MM-DD): "); // WE HAVE TO ENTER SPECIFIC DATE
	        String date = scanner.nextLine();

	        LocalDate selectedDate = LocalDate.parse(date);
	        WeatherData weatherData = weatherDataMap.get(selectedDate);
	        if (weatherData != null) {
	            System.out.println("Pressure for " + selectedDate + ": " + weatherData.getPressure() + " hPa");
	        } else {
	            System.out.println("No weather data available for " + selectedDate);
	        }
	    }

	    private void fetchWeatherData() {
	        try {
	            String https;
				URL url = new URL(API_BASE_URL);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");

	            int responseCode = connection.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                StringBuilder response = new StringBuilder();
	                String line;

	                while ((line = reader.readLine()) != null) {
	                    response.append(line);
	                }
	                reader.close();

	                parseWeatherData(response.toString());
	            } else {
	                System.out.println("Failed to fetch weather data. Response code: " + responseCode);
	            }
	            connection.disconnect();
	        } catch (IOException e) {
	            System.out.println("Error occurred while fetching weather data: " + e.getMessage());
	        }
	    }

	    private void parseWeatherData(String response) {
	       
	        LocalDate currentDate = LocalDate.now();
	        WeatherData weatherData = new WeatherData(24.5, 12.3, 1012.8);
	        weatherDataMap.put(currentDate, weatherData);
	    }

	    private static class WeatherData {
	        private double temperature;
	        private double windSpeed;
	        private double pressure;

	        public WeatherData(double temperature, double windSpeed, double pressure) {
	            this.temperature = temperature;
	            this.windSpeed = windSpeed;
	            this.pressure = pressure;
	        }

	        public double getTemperature() {
	            return temperature;
	        }

	        public double getWindSpeed() {
	            return windSpeed;
	        }

	        public double getPressure() {
	            return pressure;
	        }
	    }
	}


}
