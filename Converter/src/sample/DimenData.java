package sample;

import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Set;

public class DimenData {

    private String title;
    private HashMap<String, Double> valuesMap;
    private HashMap<String, Label> labelsMap;

    public DimenData(String title) {
        this.title = title;
        valuesMap = new HashMap<>();
        labelsMap = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public void addDimension(String title, double kf) {
        valuesMap.put(title, kf);
        labelsMap.put(title, new Label("0"));
    }

    public Set<String> getDimenTitles() {
        return valuesMap.keySet();
    }

    public double getKf(String key) {
        return valuesMap.get(key);
    }

    public Label getLabel(String key) {
        return labelsMap.get(key);
    }

    public void calculate(double value, String key) {
        double kf = valuesMap.get(key);
        for (String dim_key : valuesMap.keySet()) {
            double kf2 = valuesMap.get(dim_key);
            double result = value * (kf/kf2);
            labelsMap.get(dim_key).setText(Double.toString(result));
        }
    }


}
