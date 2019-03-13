package fr.ttvp.visuallifeconfigurator.model;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Persitance {

    private final static String OUTPUT_FOLDER   = "files";
    private final static String AUTOMATAS_FILE  = "automata_ids.txt";
    private final static String MAP_FOLDER      = "maps/";
    private final static String AUTOMATA_CONFIG = "config.xml";
    private final static String MAPS_CONFIG      = "maps.txt";

    private static Persitance instance;
    private AssetManager assetManager;
    private Context context;

    private Persitance() {

    }

    public static Persitance getInstance() {
        if(instance == null)
            instance = new Persitance();
        return instance;
    }

    /***********
     * FILE IO *
     ***********/

    public String getDevicePath(String path) {
        return this.context.getFilesDir() + "/" + OUTPUT_FOLDER + '/' + path;
    }

    public void writeFile(String data, String path) {
        try {
            File file = new File(getDevicePath(path));
            FileOutputStream fileOutput = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutput);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "Write to file failed " + e.toString());
        }
    }

    public String loadFile(String path) {
        String ret = "";

        // if the file does not exist, we copy them (all) from the asset
        File f = new File(getDevicePath(AUTOMATAS_FILE));
        if(!f.exists())
            resetFiles();

        try {
            InputStream inputStream = new FileInputStream(getDevicePath(path));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString + "\n");
            }

            inputStream.close();
            ret = stringBuilder.toString();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    /************************************************
     * FROM ASSET TO DEVICE STORAGE                 *
     * https://stackoverflow.com/a/22733626/7625364 *
     ************************************************/


    private void copyFileOrDir(String TARGET_BASE_PATH, String path) {
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(TARGET_BASE_PATH, path);
            } else {
                String fullPath =  TARGET_BASE_PATH + "/" + path;
                File dir = new File(fullPath);
                if (!dir.exists() && !path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                    dir.mkdirs();
                for (int i = 0; i < assets.length; ++i) {
                    String p;
                    if (path.equals(""))
                        p = "";
                    else
                        p = path + "/";

                    if (!path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                        copyFileOrDir(TARGET_BASE_PATH, p + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private void copyFile(String TARGET_BASE_PATH, String filename) {
        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;
        try {
            in = assetManager.open(filename);
            if (filename.endsWith(".jpg")) // extension was added to avoid compression on APK file
                newFileName = TARGET_BASE_PATH + "/" + filename.substring(0, filename.length()-4);
            else
                newFileName = TARGET_BASE_PATH + "/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of "+newFileName);
            Log.e("tag", "Exception in copyFile() "+e.toString());
        }
    }

    private void resetFiles() {
        copyFileOrDir(this.context.getFilesDir().toString(), "");
    }

    /*******
     * XML *
     *******/

    public Document getDocumentXML(String xmlPath) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        String contents = loadFile(xmlPath);
        ByteArrayInputStream input = new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
        Document doc = dBuilder.parse(input);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static String getContentXML(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }

    /************
     * AUTOMATA *
     ************/

    private String getAutomataFolder(AutomataLight automata) {
        return "automata_" + automata.getId() + "/";
    }

    private String getAutomatasFile(AutomataLight automataLight) {
        return getAutomataFolder(automataLight) + AUTOMATA_CONFIG;
    }

    public Document automataDocument(AutomataLight automataLight) {
        try {
            return this.getDocumentXML(getAutomatasFile(automataLight));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveAutomata(Automata automata) {
        String content = automata.toXML();
        String path = getAutomatasFile(automata.getAutomataLight());
        writeFile(content, path);
    }

    public List<AutomataLight> getAutomataLights() {
        List<AutomataLight> list = new ArrayList<>();
        String file = loadFile(AUTOMATAS_FILE);

        final Pattern p = Pattern.compile("(\\d+) (.+)");

        for (String s : file.split("\n")) {
            Matcher m = p.matcher(s);
            if (m.matches()){
                String id = m.group(1);
                String name = m.group(2);
                AutomataLight a = new AutomataLight(name, Integer.parseInt(id));
                list.add(a);
            }
        }

        return list;
    }

    /********
     * MAPS *
     ********/

    public List<MapLight> getCompatibleMapLights(AutomataLight automataLight) {
        List<AutomataLight> als = this.getAutomataLights();
        List<MapLight> mapLights = new ArrayList<>();
        for(AutomataLight al : als) {
            if(al.getId() != automataLight.getId()) {
                mapLights.addAll(this.getLightMaps(al));
            }
        }
        return mapLights;
    }

    // returns the maps of a particular automata
    public List<MapLight> getLightMaps(AutomataLight a) {
        final long id = a.getId();
        String mapFile = loadFile(getAutomataFolder(a) + "/" + MAPS_CONFIG);
        List<String> lines = new ArrayList<String>(
                Arrays.asList(mapFile.split("\n"))
        );
        List<MapLight> maps = new ArrayList<MapLight>();
        for (String line: lines) {
            String[] id_name = line.split(" ", 2);
            long mapid = Integer.parseInt(id_name[0]);
            maps.add(new MapLight(mapid, id_name[1], getAutomataFolder(a)));
        }
        return maps;
    }


    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void setContext(Context applicationContext) {
        this.context = applicationContext;
    }

}
