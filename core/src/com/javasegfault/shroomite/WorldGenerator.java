package com.javasegfault.shroomite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.javasegfault.shroomite.blocks.DirtBlock;
import com.javasegfault.shroomite.blocks.LavaBlock;
import com.javasegfault.shroomite.blocks.RockBlock;
import com.javasegfault.shroomite.blocks.SandBlock;
import com.javasegfault.shroomite.blocks.WaterBlock;
import com.javasegfault.shroomite.blocks.WoodBlock;
import com.javasegfault.shroomite.util.Position;

public class WorldGenerator {
    private static String fileName;

    public static World generateWorld(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return null;
        }


        try {
            String[] worldSize = reader.readLine().split(",");
            int width = Integer.parseInt(worldSize[0]);
            int height = Integer.parseInt(worldSize[1]);

            World world = new World(width, height);
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] pos = line.split(",")[0].split(":");
                String blockType = line.split(",")[1];

                int x = Integer.parseInt(pos[0]);
                int y = Integer.parseInt(pos[1]);

                Position position = new Position(x, y);

                switch (blockType) {
                    case "DIRT":
                        world.addBlock(new DirtBlock(position, world));
                        break;
                    case "LAVA":
                        world.addBlock(new LavaBlock(position, world));
                        break;
                    case "ROCK":
                        world.addBlock(new RockBlock(position, world));
                        break;
                    case "SAND":
                        world.addBlock(new SandBlock(position, world));
                        break;
                    case "WATER":
                        world.addBlock(new WaterBlock(position, world));
                        break;
                    case "WOOD":
                        world.addBlock(new WoodBlock(position, world));
                        break;
                    default:
                        break;
                }
            }

            reader.close();
            return world;
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }
}
