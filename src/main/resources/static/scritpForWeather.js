//const API_URL = "http://localhost:8080";
const API_URL = 'https://journal-entry-app-production.up.railway.app';

async function fetchWeather() {
            const city = document.getElementById('cityInput').value.trim();
            const weatherData = document.getElementById('weatherData');

            if (!city) {
                weatherData.innerHTML = '<div class="error-message">Please enter a city name</div>';
                return;
            }

            weatherData.innerHTML = '<div class="loading"><div class="spinner"></div>Loading weather data...</div>';

            try {
                const response = await fetch(`${API_URL}/public/weather?city=${encodeURIComponent(city)}`);

                if (!response.ok) {
                    throw new Error('City not found');
                }

                const data = await response.json();
                displayWeather(data);
            } catch (error) {
                weatherData.innerHTML = `<div class="error-message">⚠️ ${error.message}. Please try again.</div>`;
            }
        }

        function displayWeather(data) {
            const c = data.current;
            const l = data.location;
            const a = c.astro || {};
            const aq = c.air_quality || {};

            const html = `
                <div class="weather-container">
                    <div class="card main-card">
                        <div class="location-header">
                            <div class="location-info">
                                <h1>📍 ${l.name}</h1>
                                <p>${l.region}, ${l.country}</p>
                                <p style="font-size: 0.95em; margin-top: 5px;">🕐 ${l.localtime}</p>
                            </div>
                        </div>

                        <div class="weather-main">
                            <img src="${c.weather_icons[0]}" alt="Weather icon" class="weather-icon">
                            <div>
                                <div class="temperature">${c.temperature}°C</div>
                                <div class="weather-description">${c.weather_descriptions[0]}</div>
                                <div style="margin-top: 10px; opacity: 0.9;">Feels like ${c.feelslike}°C</div>
                            </div>
                        </div>

                        <div class="info-grid" style="margin-top: 30px;">
                            <div class="info-item">
                                <div class="info-label">💨 Wind Speed</div>
                                <div class="info-value">${c.wind_speed} km/h</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">🧭 Direction</div>
                                <div class="info-value">${c.wind_dir}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">💧 Humidity</div>
                                <div class="info-value">${c.humidity}%</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">☁️ Cloud Cover</div>
                                <div class="info-value">${c.cloudcover}%</div>
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-title">🌡️ Atmospheric Conditions</div>
                        <div class="info-grid">
                            <div class="info-item">
                                <div class="info-label">Pressure</div>
                                <div class="info-value">${c.pressure} mb</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">Precipitation</div>
                                <div class="info-value">${c.precip} mm</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">UV Index</div>
                                <div class="info-value">${c.uv_index}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">Visibility</div>
                                <div class="info-value">${c.visibility} km</div>
                            </div>
                        </div>
                    </div>

                    ${a.sunrise ? `
                    <div class="card">
                        <div class="card-title">🌙 Astronomical Data</div>
                        <div class="astro-grid">
                            <div class="astro-item">
                                <div class="astro-icon">🌅</div>
                                <div class="astro-text">
                                    <div class="astro-label">Sunrise</div>
                                    <div class="astro-value">${a.sunrise}</div>
                                </div>
                            </div>
                            <div class="astro-item">
                                <div class="astro-icon">🌇</div>
                                <div class="astro-text">
                                    <div class="astro-label">Sunset</div>
                                    <div class="astro-value">${a.sunset}</div>
                                </div>
                            </div>
                            <div class="astro-item">
                                <div class="astro-icon">🌔</div>
                                <div class="astro-text">
                                    <div class="astro-label">Moonrise</div>
                                    <div class="astro-value">${a.moonrise}</div>
                                </div>
                            </div>
                            <div class="astro-item">
                                <div class="astro-icon">🌘</div>
                                <div class="astro-text">
                                    <div class="astro-label">Moonset</div>
                                    <div class="astro-value">${a.moonset}</div>
                                </div>
                            </div>
                        </div>
                        <div class="moon-phase-display">
                            <div class="moon-phase-label">Moon Phase</div>
                            <div class="moon-phase-value">${a.moon_phase} (${a.moon_illumination}% illumination)</div>
                        </div>
                    </div>
                    ` : ''}

                    ${aq.co ? `
                    <div class="card">
                        <div class="card-title">🏭 Air Quality</div>
                        <div class="info-grid">
                            <div class="info-item">
                                <div class="info-label">CO</div>
                                <div class="info-value">${aq.co}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">NO₂</div>
                                <div class="info-value">${aq.no2}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">O₃</div>
                                <div class="info-value">${aq.o3}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-label">PM2.5</div>
                                <div class="info-value">${aq.pm2_5}</div>
                            </div>
                        </div>
                        ${aq['us-epa-index'] ? `
                        <div class="air-quality-index">
                            <span>US EPA Index:</span>
                            <span>${aq['us-epa-index']}</span>
                        </div>
                        ` : ''}
                    </div>
                    ` : ''}

                    <div class="card">
                        <div class="card-title">📍 Location Details</div>
                        <div class="location-details">
                            <p><strong>Coordinates:</strong> ${l.lat}°N, ${l.lon}°E</p>
                            <p><strong>Timezone:</strong> ${l.timezone_id}</p>
                            <p><strong>UTC Offset:</strong> ${l.utc_offset}</p>
                            <p><strong>Observation Time:</strong> ${c.observation_time}</p>
                            <p><strong>Day/Night:</strong> ${c.is_day === 'yes' ? '☀️ Day' : '🌙 Night'}</p>
                        </div>
                    </div>
                </div>
            `;

            document.getElementById('weatherData').innerHTML = html;
        }

        // Load default weather on page load
        window.onload = () => fetchWeather();