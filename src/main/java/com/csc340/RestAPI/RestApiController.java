package com.csc340.RestAPI;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestApiController {

    /**
     * Get a random cat image from thecatapi and make it available at this endpoint.
     *
     * @return an html page with the image and formatting
     */
    @GetMapping("/")
    public Object getCat() {
        String url = "https://api.thecatapi.com/v1/images/search";
        RestTemplate restTemplate = new RestTemplate();

        String cat = restTemplate.getForObject(url, String.class);
        JSONObject jo = new JSONArray(cat).getJSONObject(0);
        String catUrl = jo.getString("url");
        int width = jo.getInt("width");
        int height = jo.getInt("height");

        // Normalize the height of the image to 700px
        width = (int) (width * (500.0 / height));
        height = 500;


        return """
                <!DOCTYPE html>
                <html>
                <head>
                <title>Random Cat</title>
                <style>
                body  {
                        background-color: #f2f2f2;
                        align-items: center;
                        justify-content: center;
                        display: flex;
                        flex-direction: column;
                        }
                img {
                        border-radius: 10px;
                        border: 1px solid #ddd;
                        padding: 5px;
                        box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2);
                        }
                h1 {
                        font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
                        color: #AFAA6A;
                        }
                </style>
                </head>
                <body>
                <h1>Random Cat</h1>
                <img src="%s" alt="" width=%s height=%s>
                                
                </body>
                </html>
                                
                """.formatted(catUrl, width, height);
    }


}
