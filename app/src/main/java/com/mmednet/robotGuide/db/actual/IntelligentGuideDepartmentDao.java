package com.mmednet.robotGuide.db.actual;

import android.content.Context;

import com.mmednet.robotGuide.bean.IntelligentGuideDepartment;
import com.mmednet.robotGuide.db.CommonDaoImpl;

/**
 * Created by alpha on 2016/9/2.
 */
public class IntelligentGuideDepartmentDao extends CommonDaoImpl<IntelligentGuideDepartment> {

    public IntelligentGuideDepartmentDao(Context context){
        super.init(context);
    }

}
