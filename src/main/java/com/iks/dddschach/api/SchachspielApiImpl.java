package com.iks.dddschach.api;

import com.iks.dddschach.domain.SchachbrettValueObject;
import com.iks.dddschach.domain.SpielIdValueObject;
import com.iks.dddschach.domain.HalbzugValueObject;


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
    public int fuehreHalbzugAus(SpielIdValueObject spielId, HalbzugValueObject zug) {
        // TODO: Zu implementieren
        return -1;
    }


    @Override
    public SchachbrettValueObject schachBrett(SpielIdValueObject gameId) {
        // TODO: Zu implementieren
        return new SchachbrettValueObject();
    }

}
