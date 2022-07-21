package net.aplat.pb.common;

import net.aplat.pb.exception.IllegalGroupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PictureBrowserConf {

    @Value("#{${picture-browser.groups}}")
    private Map<String, String> groups;

    public String getGroup(String name) throws IllegalGroupException {
        if (groups.containsKey(name)) {
            return groups.get(name);
        }
        throw new IllegalGroupException("The group name '" + name + "' not found!");
    }

    public Map<String, String> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }
}
