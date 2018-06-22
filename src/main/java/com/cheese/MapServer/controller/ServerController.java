package com.cheese.MapServer.controller;

import com.cheese.MapServer.bean.MapConfig;
import com.cheese.MapServer.utils.FileUtils;
import com.cheese.MapServer.utils.InitUtils;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/server")
public class ServerController
{
    @RequestMapping(
            value = "/map/{type}/{x}/{y}/{z}",
            method = RequestMethod.GET,
            produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE}
            )
    public byte[] initMap(@PathVariable Integer x, @PathVariable Integer y,
                        @PathVariable Integer z, @PathVariable String type) throws IOException
    {
        String path = InitUtils.getPathByType(InitUtils.getTypeByName(type));

        String imgPath = path + z + "/" + x + "/" + y + "/img.jpg";
        File file = new File(imgPath);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        inputStream.read(data);
        inputStream.close();

        return data;
    }

    @RequestMapping("/config")
    public MapConfig getConfig()
    {
        try
        {
            File configFile = new File("map/map.config.json");
            MapConfig mapConfig = new MapConfig();
            if(configFile.exists())
                mapConfig = new Gson().fromJson(FileUtils.readFileByUtf8("map/map.config.json"),
                        MapConfig.class);

            return mapConfig;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new MapConfig();
    }
}
