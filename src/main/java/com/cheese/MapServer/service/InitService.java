package com.cheese.MapServer.service;

import com.cheese.MapServer.bean.MapConfig;
import com.cheese.MapServer.bean.ThreadReqParamInfo;
import com.cheese.MapServer.utils.BackgroundType;
import com.cheese.MapServer.utils.FileUtils;
import com.cheese.MapServer.utils.InitUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InitService
{
    @Value("#{'${map-server.init-dirs}'.split(',')}")
    public List<String> initCreateDirs;

    @Value("${map-server.config-file}")
    public String configFilePath;

    @PostConstruct
    public void init() {
        for (String dirPath : initCreateDirs) {
            File create = new File(dirPath.trim());
            if(!create.exists()) {
                create.mkdir();
            }
        }
    }

    public List<ThreadReqParamInfo> getLevelPic(BackgroundType type, Integer level, Double left,
                               Double right, Double top, Double bottom)
    {
        List<ThreadReqParamInfo> errorList = InitUtils.getLevelPic(type, level, left, right, top, bottom);
        addConfig(type, level, left, right, top, bottom);

        return errorList;
    }

    public void addConfig(BackgroundType type, Integer level, Double left,
                             Double right, Double top, Double bottom)
    {
        try
        {
            File configFile = new File(configFilePath);
            MapConfig mapConfig = new MapConfig();
            if(configFile.exists())
                mapConfig = new Gson().fromJson(FileUtils.readFileByUtf8(configFilePath),
                        MapConfig.class);

            MapConfig.PicArea area = new MapConfig.PicArea();
            area.setLeft(left);
            area.setRight(right);
            area.setBottom(bottom);
            area.setTop(top);

            if(mapConfig.getConfig().containsKey(type))
                mapConfig.getConfig().get(type).put(level, area);
            else
            {
                Map<Integer, MapConfig.PicArea> levelArea = new HashMap<>();
                levelArea.put(level, area);
                mapConfig.getConfig().put(type, levelArea);
            }

            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(new Gson().toJson(mapConfig));
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
