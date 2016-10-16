package com.mmednet.robotGuide.db.actual;

import android.content.Context;

import com.mmednet.robotGuide.bean.IntelligentGuideSymptoms;
import com.mmednet.robotGuide.db.CommonDaoImpl;

/**
 * Created by alpha on 2016/9/2.
 */
public class IntelligentGuideSymptomsDao extends CommonDaoImpl<IntelligentGuideSymptoms> {

    public IntelligentGuideSymptomsDao(Context context){
        super.init(context);
    }

}
