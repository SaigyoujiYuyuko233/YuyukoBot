package network.meikai.mc.uuzselfplaybot.update;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckUpdates {

    public boolean check() throws IOException {

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
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // parse json
        String latest = (String) JSON.parseObject(
                JSON.parseArray(response.toString()).get(0).toString()
        ).get("tag_name");

        if ( GlobalVars.GIT_BRANCH == null || !GlobalVars.GIT_BRANCH.equals(latest) ) {
            System.out.println("[" + Ansi.ansi().fgBright(Ansi.Color.GREEN).a("+").reset() + "] "
                    + Ansi.ansi().fgBright(Ansi.Color.BLUE).a("新版本已发布!").reset() + " 最新版本 [" + latest + "]");
            System.out.println("[" + Ansi.ansi().fgBright(Ansi.Color.GREEN).a("+").reset() + "] "
                    + Ansi.ansi().fgBright(Ansi.Color.BLUE).a("发布地址: https://gitlab.uuzdream.cn/uuz/uuzselfplaybot/-/releases").reset());
        }

        return true;
    }


}
