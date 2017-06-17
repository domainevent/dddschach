package com.iks.dddschach.domain.validation;

/**
 * @author javacook
 */
public enum Zugregel {
    DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN,
    STARTFELD_MUSS_SPIELFIGUR_ENTHALTEN,
    ZUG_STRECKE_MUSS_FREI_SEIN,
    EIGENE_FIGUREN_LASSEN_SICH_NICHT_SCHLAGEN,
    KOENIG_KANN_NICHT_GESCHLAGEN_WERDEN,
    LAUEFER_ZIEHT_DIAGONAL,
    TURM_ZIEHT_GERADE,
    DAME_ZIEHT_GERADE_ODER_DIAGONAL,
    KOENIG_ZIEHT_NUR_EIN_FELD,
    SPRINGER_ZIEHT_EINEN_WINKEL,
    BAUER_SCHLAEGT_NUR_SCHRAEG,
    BAUER_ZIEHT_NUR_GEREADE_FALLS_ER_NICHT_SCHLAEGT,
    BAUER_ZIEHT_EIN_FELD_VORWAERTS_AUSSER_AM_ANFANG_ZWEI,
    BAUER_ZIEHT_EIN_FELD_VORWAERTS_FALLS_ER_SCHLAEGT,
    BAUER_ZIEHT_MAXIMAL_EIN_FELD_SEITWAERTS,
    KOENIG_STEHT_IM_SCHACH,
    HALBZUG_IST_KEINE_ROCHADE,
    ROCHADE_FELD_STEHT_IM_SCHACH,
    ROCHADE_KOENIG_WURDE_BEREITS_BEWEGT,
    ROCHADE_TURM_WURDE_BEREITS_BEWEGT,
    HALBZUG_IST_KEIN_EN_PASSANT,
    EN_PASSANT_VORGAENGER_HALBZUG_MUSS_BAUER_SEIN,
    DIE_PARTIE_ENDED_MATT,
    DIE_PARTIE_ENDED_PATT
}
