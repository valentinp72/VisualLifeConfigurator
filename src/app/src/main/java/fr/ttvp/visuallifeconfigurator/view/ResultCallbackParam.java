package fr.ttvp.visuallifeconfigurator.view;

import java.io.Serializable;

public interface ResultCallbackParam<T> extends Serializable {

    public void ended(T param);

}
