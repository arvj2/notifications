package com.jvra.material;

import android.app.Fragment;

/**
 * Created by Jansel R. Abreu on 4/17/2015.
 */
public class OtherMetadataFragment extends Fragment{

    public static OtherMetadataFragment newInstance(){
        OtherMetadataFragment fragment = new OtherMetadataFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }
}
