package com.app.instancedownload.interfaces;

import java.io.Serializable;

public interface OnClick extends Serializable {

    void show(int position, String type, String data);

}
