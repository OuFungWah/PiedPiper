package com.crazywah.piedpiper.base;

import android.content.Intent;
import android.support.annotation.NonNull;

public interface BaseResultDependence {

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    void onActivityResult(int requestCode, int resultCode, Intent data);

}
