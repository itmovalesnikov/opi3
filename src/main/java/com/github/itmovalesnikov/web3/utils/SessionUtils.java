package com.github.itmovalesnikov.web3.utils;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static String getSessionId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
            throw new IllegalStateException("FacesContext is not available");
        }

        HttpSession session = (HttpSession) facesContext
                .getExternalContext()
                .getSession(true);

        return session.getId();
    }
}
