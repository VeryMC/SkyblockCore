package main.java.fr.verymc.storage;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AsyncConfig {

    public static AsyncConfig instance;

    public AsyncConfig() {
        instance = this;
    }

    public void setAndSaveAsync(HashMap<String, Object> hashMap, FileConfiguration fileConfiguration, File file) {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                for (Map.Entry<String, Object> objectEntry : hashMap.entrySet()) {
                    fileConfiguration.set(objectEntry.getKey(), objectEntry.getValue());
                }
                try {
                    fileConfiguration.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0);
    }

    /*public ArrayList<Object> getObjectsStartup(FileConfiguration fileConfiguration, File file) {
        ArrayList<Object> objects = new ArrayList<>();
        //CompletableFuture<T>#supplyAsync(Supplier<T>)
        CompletableFuture.supplyAsync(() -> {
            //tries to fetch data from a database which doesn’t block the main thread but another thread.
            for (String key : fileConfiguration.getKeys(false)) {
                objects.add(fileConfiguration.get(key));
            }
            return objects;
        }).join(); //makes it blocking

        //never used
        return null;
    }*/
}
