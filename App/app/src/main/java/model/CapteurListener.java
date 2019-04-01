package model;

import java.util.EventListener;

public interface CapteurListener extends EventListener {
    void onValueChanged(float x, float y, float z, int sensi);
}
