package pl.politechnika.goalreacher.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class SendNotfications
{
    public static final String REST_API_KEY = "MGNjNjEzY2EtY2MxMy00NzQzLWIwYWYtZmRjYmZmOWUzOTk4";
    public static final String APP_ID = "6880a7bd-381a-4018-a17a-3dc8c91d3c5b";

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException
    {
        String jsonResponse;
        if (httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST)
        {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        } else
        {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }

    // users: lista oneSignalPlayerId z encji AppUser do ktorych ma byc wysłane powiadomienie
    // data: "{"goto": "targetScene", "group": "targetGroup(if necessary)"}
    public static void sendMessageToUsers(String message, List<String> users, String data)
    {
        try
        {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", REST_API_KEY);
            con.setRequestMethod("POST");

            StringBuilder userIds = new StringBuilder("[");

            for (String user : users)
            {
                userIds.append("\"").append(user).append("\"").append(",");
            }

            userIds.deleteCharAt(userIds.length() - 1);
            userIds.append("]");

            String strJsonBody = "{"
                    + "\"app_id\": \"" + APP_ID + "\","
                    + "\"include_player_ids\": " + userIds.toString() + ","
                    + "\"data\":" + data + ","
                    + "\"contents\": {\"en\": \"" + message + "\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            jsonResponse = mountResponseRequest(con, httpResponse);
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
