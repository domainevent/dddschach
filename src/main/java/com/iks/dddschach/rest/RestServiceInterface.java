package com.iks.dddschach.rest;

import com.iks.dddschach.api.SchachpartieApi.UngueltigeSpielIdException;
import com.iks.dddschach.api.SchachpartieApi.UngueltigerHalbzugException;
import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.SpielId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;


/**
 * Created by vollmer on 05.07.17.
 */
public interface RestServiceInterface {

    String isAlive();

    SpielId neuesSpiel(@Size(max = 100) @FormParam("note") String vermerk);

    Response spielbrett(String spielId,
                        String clientId,
                        Request request)
            throws UngueltigeSpielIdException;


    Response fuehreHalbzugAus(
            String spielId,
            @NotNull(message = "The form parameter move is mandatory.") String halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException;


    Response fuehreHalbzugAus(
             String spielId,
             @NotNull(message = "A body of type Halbzug is required.") @Valid Halbzug halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException;
}
