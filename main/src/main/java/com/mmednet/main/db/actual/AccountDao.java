package com.mmednet.main.db.actual;

import android.content.Context;

import com.mmednet.main.bean.Account;
import com.mmednet.main.db.CommonDaoImpl;

/**
 * Created by alpha on 2016/9/22.
 */
public class AccountDao extends CommonDaoImpl<Account> {

    public AccountDao(Context context) {
        super.init(context);
    }
}
