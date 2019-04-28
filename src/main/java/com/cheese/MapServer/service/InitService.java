package com.cheese.MapServer.service;

import com.cheese.MapServer.bean.MapConfig;
import com.cheese.MapServer.bean.ThreadReqParamInfo;
import com.cheese.MapServer.utils.BackgroundType;
import com.cheese.MapServer.utils.FileUtils;
import com.cheese.MapServer.utils.InitUtils;
import com.google.gson.Gson;
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
    @PostConstruct
    public void init()
    {
        createDir();
    }

    private void createDir()
    {
        File create = new File("map");
        if(!create.exists())create.mkdir();

        create = new File("map/google");
        if(!create.exists())create.mkdir();

        create = new File("map/google/satellite");
        if(!create.exists())create.mkdir();

        create = new File("map/google/image");
        if(!create.exists())create.mkdir();

        create = new File("map/google/terrain");
        if(!create.exists())create.mkdir();

        create = new File("map/amap");
        if(!create.exists())create.mkdir();

        create = new File("map/amap/satellite");
        if(!create.exists())create.mkdir();

        create = new File("map/amap/image");
        if(!create.exists())create.mkdir();

        create = new File("map/amap/cover");
        if(!create.exists())create.mkdir();

        create = new File("map/tianditu");
        if(!create.exists())create.mkdir();

        create = new File("map/tianditu/satellite");
        if(!create.exists())create.mkdir();

        create = new File("map/tianditu/image");
        if(!create.exists())create.mkdir();

        create = new File("map/tianditu/cover");
        if(!create.exists())create.mkdir();
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
            File configFile = new File("map/map.config.json");
            MapConfig mapConfig = new MapConfig();
            if(configFile.exists())
                mapConfig = new Gson().fromJson(FileUtils.readFileByUtf8("map/map.config.json"),
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
