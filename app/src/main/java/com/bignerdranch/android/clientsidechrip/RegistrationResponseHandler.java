package com.bignerdranch.android.clientsidechrip;

import java.util.Date;
import java.util.UUID;

public interface RegistrationResponseHandler
{
    public void handleResponse(boolean isRegistrationValid);

    class Chirp {

        String message, email, handle;
        Date date;
        UUID id;

        public Chirp(String em, String m, String h, Date d)
        {
            email = em;
            message = m;
            handle = h;
            date = d;
        }

        public String getMessage()
        {
            return message;
        }

        public String getEmail()
        {
            return email;
        }

        public String getHandle;

        public Date getDate()
        {
            return date;
        }

    }
}
