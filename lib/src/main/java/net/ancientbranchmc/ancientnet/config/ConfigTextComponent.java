package net.ancientbranchmc.ancientnet.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigTextComponent {
    private List<String> stringList;

    public ConfigTextComponent(FileConfiguration config, String path) {
        stringList = config.getStringList(path);
    }

    public String getFullJsonString() {
        StringBuilder fullJson = new StringBuilder();
        fullJson.append("[");

        for(int i = 0; i < stringList.size(); i++) {
            String s = stringList.get(i);

            if(s.startsWith("{") && s.endsWith("}")) {
                fullJson.append(s);
            }
            else {
                fullJson.append("{\"text\":\"").append(s.replace("\"", "\\\"")).append("\"}");
            }

            if(i < stringList.size() - 1) {
                fullJson.append(",");
            }
        }

        fullJson.append("]");

        return fullJson.toString();
    }

    public Component getComponent() {
        return GsonComponentSerializer.gson().deserialize(getFullJsonString());
    }
}
