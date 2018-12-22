package rest;

import android.view.View;

/*
* Interface to control each item on recycler view.
* */

public interface ItemClickListener {

    void onItemClick(View v, int pos);
}
