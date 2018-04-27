package com.bignerdranch.android.clientsidechrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaffer on 3/28/18.
 */

public interface ListUsersResponseHandler
{
    public void handleResponse(List<Chirp> chirps);
}