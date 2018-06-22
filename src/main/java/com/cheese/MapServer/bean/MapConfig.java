package com.cheese.MapServer.bean;

import com.cheese.MapServer.utils.BackgroundType;

import java.util.HashMap;
import java.util.Map;

public class MapConfig
{
    public Map<BackgroundType, Map<Integer, PicArea>> getConfig()
    {
        return config;
    }

    public void setConfig(Map<BackgroundType, Map<Integer, PicArea>> config)
    {
        this.config = config;
    }

    private Map<BackgroundType, Map<Integer, PicArea>> config = new HashMap<>();


    public static class PicArea
    {
        public Double getLeft()
        {
            return left;
        }

        public void setLeft(Double left)
        {
            this.left = left;
        }

        public Double getRight()
        {
            return right;
        }

        public void setRight(Double right)
        {
            this.right = right;
        }

        public Double getTop()
        {
            return top;
        }

        public void setTop(Double top)
        {
            this.top = top;
        }

        public Double getBottom()
        {
            return bottom;
        }

        public void setBottom(Double bottom)
        {
            this.bottom = bottom;
        }

        private Double left;
        private Double right;
        private Double top;
        private Double bottom;
    }
}
