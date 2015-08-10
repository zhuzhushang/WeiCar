package com.newbrain.model;

public class Model_Weather {

	/**
	 * { "resultcode": "200", "reason": "successed!", "result": { "sk": {
	 * "temp": "30", "wind_direction": "东风", "wind_strength": "1级", "humidity":
	 * "73%", "time": "15:08" }, "today": { "temperature": "26℃~30℃", "weather":
	 * "中雨", "weather_id": { "fa": "08", "fb": "08" }, "wind": "微风", "week":
	 * "星期二", "city": "深圳", "date_y": "2015年06月23日", "dressing_index": "热",
	 * "dressing_advice": "天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。", "uv_index": "弱",
	 * "comfort_index": "", "wash_index": "不宜", "travel_index": "",
	 * "exercise_index": "较不宜", "drying_index": "" },
	 * 
	 * }, "error_code": 0 }
	 * 
	 * 
	 * */

	private String resultcode;
	private String reason;

	private Result result;

	private int error_code;

	public class Result {

		private SK sk;
		private Today today;

		public class SK {
			private String temp;
			private String wind_direction;
			private String wind_strength;
			private String humidity;
			private String time;

			public String getTemp() {
				return temp;
			}

			public void setTemp(String temp) {
				this.temp = temp;
			}

			public String getWind_direction() {
				return wind_direction;
			}

			public void setWind_direction(String wind_direction) {
				this.wind_direction = wind_direction;
			}

			public String getWind_strength() {
				return wind_strength;
			}

			public void setWind_strength(String wind_strength) {
				this.wind_strength = wind_strength;
			}

			public String getHumidity() {
				return humidity;
			}

			public void setHumidity(String humidity) {
				this.humidity = humidity;
			}

			public String getTime() {
				return time;
			}

			public void setTime(String time) {
				this.time = time;
			}

		}

		public class Today {

			private String temperature;
			private String weather;
			private String wind;
			private String week;
			private String city;
			private String date_y;
			private String dressing_index;
			private String dressing_advice;
			private String uv_index;
			private String comfort_index;
			private String wash_index;
			private String travel_index;
			private String exercise_index;
			private String drying_index;

			private Weather_id weather_id;

			public class Weather_id {

				String fa;
				String fb;

				public String getFa() {
					return fa;
				}

				public void setFa(String fa) {
					this.fa = fa;
				}

				public String getFb() {
					return fb;
				}

				public void setFb(String fb) {
					this.fb = fb;
				}

			}

			public String getTemperature() {
				return temperature;
			}

			public void setTemperature(String temperature) {
				this.temperature = temperature;
			}

			public String getWeather() {
				return weather;
			}

			public void setWeather(String weather) {
				this.weather = weather;
			}

			public String getWind() {
				return wind;
			}

			public void setWind(String wind) {
				this.wind = wind;
			}

			public String getWeek() {
				return week;
			}

			public void setWeek(String week) {
				this.week = week;
			}

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getDate_y() {
				return date_y;
			}

			public void setDate_y(String date_y) {
				this.date_y = date_y;
			}

			public String getDressing_index() {
				return dressing_index;
			}

			public void setDressing_index(String dressing_index) {
				this.dressing_index = dressing_index;
			}

			public String getDressing_advice() {
				return dressing_advice;
			}

			public void setDressing_advice(String dressing_advice) {
				this.dressing_advice = dressing_advice;
			}

			public String getUv_index() {
				return uv_index;
			}

			public void setUv_index(String uv_index) {
				this.uv_index = uv_index;
			}

			public String getComfort_index() {
				return comfort_index;
			}

			public void setComfort_index(String comfort_index) {
				this.comfort_index = comfort_index;
			}

			public String getWash_index() {
				return wash_index;
			}

			public void setWash_index(String wash_index) {
				this.wash_index = wash_index;
			}

			public String getTravel_index() {
				return travel_index;
			}

			public void setTravel_index(String travel_index) {
				this.travel_index = travel_index;
			}

			public String getExercise_index() {
				return exercise_index;
			}

			public void setExercise_index(String exercise_index) {
				this.exercise_index = exercise_index;
			}

			public String getDrying_index() {
				return drying_index;
			}

			public void setDrying_index(String drying_index) {
				this.drying_index = drying_index;
			}

			public Weather_id getWeather_id() {
				return weather_id;
			}

			public void setWeather_id(Weather_id weather_id) {
				this.weather_id = weather_id;
			}

		}

		public SK getSk() {
			return sk;
		}

		public void setSk(SK sk) {
			this.sk = sk;
		}

		public Today getToday() {
			return today;
		}

		public void setToday(Today today) {
			this.today = today;
		}

	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	/*
	 * "result": { "sk": { "temp": "30", "wind_direction": "东风",
	 * "wind_strength": "1级", "humidity": "73%", "time": "15:08" }, "today": {
	 * "temperature": "26℃~30℃", "weather": "中雨", "weather_id": { "fa": "08",
	 * "fb": "08" }, "wind": "微风", "week": "星期二", "city": "深圳", "date_y":
	 * "2015年06月23日", "dressing_index": "热", "dressing_advice":
	 * "天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。", "uv_index": "弱", "comfort_index": "",
	 * "wash_index": "不宜", "travel_index": "", "exercise_index": "较不宜",
	 * "drying_index": "" },
	 * 
	 * }, "error_code": 0 }
	 */

}
