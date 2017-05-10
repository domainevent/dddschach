package com.iks.dddschach.api;

import com.iks.dddschach.domain.SchachbrettValueObject;
import com.iks.dddschach.domain.SpielIdValueObject;
import com.iks.dddschach.domain.ZugValueObject;


/**
 * Created by javacook on 21.04.17.
 */
public class SchachspielApiImpl implements SchachspielApi {

    @Override
    public SpielIdValueObject neuesSpiel() {
        // TODO: Zu implementieren
        return new SpielIdValueObject("spielIdDummy");
    }


    @Override
    public int fuehreZugAus(SpielIdValueObject spielId, ZugValueObject zug) {
        // TODO: Zu implementieren
        return -1;
    }


    @Override
    public SchachbrettValueObject schachBrett(SpielIdValueObject gameId) {
        // TODO: Zu implementieren
        return new SchachbrettValueObject();
    }

}
