package network.meikai.mc.uuzselfplaybot.update;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CheckUpdates {

    public boolean check() {
        try {

            // http request
            HttpURLConnection connection = (HttpURLConnection) new URL(GlobalVars.RELEASE_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.connect();
            if (connection.getResponseCode() != 200) {
                System.out.println("[" + Ansi.ansi().fgBright(Ansi.Color.GREEN).a("+").reset() + "] "
                        + Ansi.ansi().fgBright(Ansi.Color.YELLOW).a("无法从远端获取更新!").reset() + "[" + connection.getResponseCode() + "]");
                return false;
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            // parse json
            String latest = JSON.parseArray(response.toString()).getJSONObject(0).getString("tag_name");

            if ( GlobalVars.GIT_BRANCH == null || !GlobalVars.GIT_BRANCH.equals(latest) ) {
                System.out.println("[" + Ansi.ansi().fgBright(Ansi.Color.GREEN).a("+").reset() + "] "
                        + Ansi.ansi().fgBright(Ansi.Color.BLUE).a("新版本已发布!").reset() + " 最新版本 [" + latest + "]");
                System.out.println("[" + Ansi.ansi().fgBright(Ansi.Color.GREEN).a("+").reset() + "] "
                        + Ansi.ansi().fgBright(Ansi.Color.BLUE).a("发布地址: https://gitlab.uuzdream.cn/uuz/uuzselfplaybot/-/releases").reset());
            }

            if ( GlobalVars.GIT_BRANCH != null && GlobalVars.GIT_BRANCH.equals(latest) ) {
                System.out.println("[" + Ansi.ansi().fgBright(Ansi.Color.GREEN).a("+").reset() + "] "
                        + Ansi.ansi().fgBright(Ansi.Color.BLUE).a("您使用的是最新版本!").reset() + " [" + latest + "]");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
