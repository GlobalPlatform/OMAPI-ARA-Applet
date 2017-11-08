/*************************************************************************************
Copyright (c) 2017 GlobalPlatform Inc. All Rights Reserved.
Recipients of this document are invited to submit, with their comments, notification 
of any relevant patent rights or other intellectual property rights of which they may 
be aware which might be infringed by the implementation of the specification set forth 
in this document, and to provide supporting documentation. The technology provided or 
described herein is subject to updates, revisions, and extensions by GlobalPlatform. 
Use of this information is governed by the GlobalPlatform license agreement and any 
use inconsistent with that agreement is strictly prohibited.

1. GRANT OF LICENSE.
1.1 License. GlobalPlatform hereby grants to Licensee, its Affiliates and End Users, 
a non-exclusive, perpetual (except as provided for herein), royalty-free, fully paid-up, 
worldwide license in the GP Test Product(s) purchased by it for purposes of testing  
secure chips and/or TEEs for compliance with the relevant GlobalPlatform Card, Device 
or Systems related product or component.

1.2 Modifications. Licensee, its Affiliates and End Users shall not modify the GP Test 
Products.

1.3 Sublicensing. Licensee, its Affiliates and End Users, may sublicense the GP Test 
Products provided that any sublicensee agrees to be bound by the terms of this Agreement, 
and if such sublicensee is not a member of GP, such sublicensee pays the then-current 
purchase price for such license to ICCS. Any Licensee, Affiliate or End User that 
sublicenses any GP Test Product shall report such sublicense and the name of any such 
sublicensee to GP immediately after the effectiveness of such sublicense.

2. INTELLECTUAL PROPERTY. Licensee acknowledges and agrees that, as between Licensee 
and GlobalPlatform, all GP Test Products shall at all times be the exclusive property 
of GlobalPlatform, and nothing in this Agreement shall be construed to convey to 
Licensee any ownership interest in the GP Test Products.
*************************************************************************************/

/* ----------------------------------------------------------------------------
 *   CHANGE HISTORY:
 *
 * 2017/11/08:
 * 	- First version.
 *
 * ---------------------------------------------------------------------------- */

package org.globalplatform.test.ara;

import javacard.framework.*;

public class ARAapplet extends Applet implements MultiSelectable {

    static final byte INS_GET_DATA = (byte) 0xCA;
    static final short TAG_CMD_GET_NEXT = (short) 0xFF60;
    static final short TAG_CMD_GET_SPECIFIC = (short) 0xFF50;
    static final short TAG_CMD_GET_ALL = (short) 0xFF40;
    static final short TAG_CMD_GET_REFRESH = (short) 0xDF20;
    private final static byte[] RESPONSE_GET_REFRESH = { (byte) 0xDF,
            (byte) 0x20, (byte) 0x08, (byte) 0x01, (byte) 0x02, (byte) 0x03,
            (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08 };
    private final static byte[] COMMAND_GET_SPECIFIC_DENIED = { (byte) 0x80,
            (byte) 0xCA, (byte) 0xFF, (byte) 0x50, (byte) 0x11, (byte) 0xE1,
            (byte) 0x0F, (byte) 0x4F, (byte) 0x0B, (byte) 0xA0, (byte) 0x00,
            (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x00,
            (byte) 0x01, (byte) 0xEE, (byte) 0x05, (byte) 0xFE, (byte) 0xC1,
            (byte) 0x00, (byte) 0x00 };
    private final static byte[] RESPONSE_GET_SPECIFIC_DENIED = { (byte) 0xFF,
            (byte) 0x50, (byte) 0x08, (byte) 0xE3, (byte) 0x06, (byte) 0xD0,
            (byte) 0x01, (byte) 0x00, (byte) 0xD1, (byte) 0x01, (byte) 0x00 };
    private final static byte[] COMMAND_GET_SPECIFIC_TestApp = { (byte) 0x80,
            (byte) 0xCA, (byte) 0xFF, (byte) 0x50, (byte) 0x11, (byte) 0xE1,
            (byte) 0x0F, (byte) 0x4F, (byte) 0x0B, (byte) 0xA0, (byte) 0x00,
            (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x00,
            (byte) 0x01, (byte) 0xEE, (byte) 0x05, (byte) 0x01, (byte) 0xC1,
            (byte) 0x00, (byte) 0x00 };
    private final static byte[] RESPONSE_GET_SPECIFIC_TestApp = {
            (byte) 0xFF, (byte) 0x50, (byte) 0x3F,
                (byte) 0xE3, (byte) 0x3D,
                    (byte) 0xD0, (byte) 0x38,
                        (byte) 0x00, (byte) 0x10, (byte) 0x01, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x10, (byte) 0x02, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFB, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x70, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0x7F, (byte) 0xE0,
                    (byte) 0xD1, (byte) 0x01, (byte) 0x00 };
    private final static byte[] RESPONSE_GET_SPECIFIC_OTHERS = { (byte) 0xFF,
            (byte) 0x50, (byte) 0x08, (byte) 0xE3, (byte) 0x06, (byte) 0xD0,
            (byte) 0x01, (byte) 0x01, (byte) 0xD1, (byte) 0x01, (byte) 0x01 };
    private final static byte[] RESPONSE_GET_ALL = {
        (byte) 0xFF, (byte) 0x40, (byte) 0x7D,
            // Rules for AID A0 00 00 06 00 01 00 01 EE 05 FE
            (byte) 0xE2, (byte) 0x19,
                (byte) 0xE1, (byte) 0x0F,
                    (byte) 0x4F, (byte) 0x0B,
                        (byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x06,
                        (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01,
                        (byte) 0xEE, (byte) 0x05, (byte) 0xFE,
                    (byte) 0xC1, (byte) 0x00,
                (byte) 0xE3, (byte) 0x06,
                    (byte) 0xD0, (byte) 0x01, (byte) 0x00,
                    (byte) 0xD1, (byte) 0x01, (byte) 0x00,
            // Rules for AID A0 00 00 06 00 01 00 01 EE 05 01
            (byte) 0xE2, (byte) 0x50,
                (byte) 0xE1, (byte) 0x0F,
                    (byte) 0x4F, (byte) 0x0B,
                        (byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x06,
                        (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01,
                        (byte) 0xEE, (byte) 0x05, (byte) 0x01,
                    (byte) 0xC1, (byte) 0x00,
                (byte) 0xE3, (byte) 0x3D,
                    (byte) 0xD0, (byte) 0x38,
                        (byte) 0x00, (byte) 0x10, (byte) 0x01, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x10, (byte) 0x02, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0xFB, (byte) 0xFF,
                        (byte) 0x00, (byte) 0x70, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0xFF, (byte) 0x7F, (byte) 0xE0,
                    (byte) 0xD1, (byte) 0x01, (byte) 0x00,
            // Rules for all applets (allow all)
            (byte) 0xE2, (byte) 0x0E,
                (byte) 0xE1, (byte) 0x04,
                    (byte) 0x4F, (byte) 0x00,
                    (byte) 0xC1, (byte) 0x00,
                (byte) 0xE3, (byte) 0x06,
                    (byte) 0xD0, (byte) 0x01, (byte) 0x01,
                    (byte) 0xD1, (byte) 0x01, (byte) 0x01
    };

    public static void install(byte[] abArray, short sOffset, byte bLength) {
        (new ARAapplet()).register(abArray, (short) (sOffset + 1), abArray[sOffset]);
    }

    public boolean select(boolean appInstAlreadyActive) {
        return true;
    }

    public void deselect(boolean appInstStillActive) {
        return;
    }

    public void process(APDU oAPDU) throws ISOException {
        short sSW1SW2 = ISO7816.SW_NO_ERROR;
        short sOutLength = (short) 0;

        byte[] abData = oAPDU.getBuffer();

        byte bINS = abData[ISO7816.OFFSET_INS];

        short sMode = Util.getShort(abData, ISO7816.OFFSET_P1);

        if (selectingApplet() == true) {
            return;
        }

        try {
            if (bINS != INS_GET_DATA) {
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
            }

            if (sMode == TAG_CMD_GET_ALL) {
                sOutLength = Util.arrayCopyNonAtomic(RESPONSE_GET_ALL,
                        (short) 0,
                        abData,
                        (short) 0,
                        (short) RESPONSE_GET_ALL.length);
            } else if (sMode == TAG_CMD_GET_SPECIFIC) {
                short numBytes = oAPDU.setIncomingAndReceive();
                if (Util.arrayCompare(abData, (short) 0, COMMAND_GET_SPECIFIC_DENIED, (short) 0, numBytes) == 0) {
                    sOutLength = Util.arrayCopyNonAtomic(RESPONSE_GET_SPECIFIC_DENIED,
                            (short) 0,
                            abData,
                            (short) 0,
                            (short) RESPONSE_GET_SPECIFIC_DENIED.length);
                } else if(Util.arrayCompare(abData, (short) 0, COMMAND_GET_SPECIFIC_TestApp, (short) 0, numBytes) == 0){
                     sOutLength = Util.arrayCopyNonAtomic(RESPONSE_GET_SPECIFIC_TestApp,
                            (short) 0,
                            abData,
                            (short) 0,
                            (short) RESPONSE_GET_SPECIFIC_TestApp.length);    
                } else { 
                    sOutLength = Util.arrayCopyNonAtomic(RESPONSE_GET_SPECIFIC_OTHERS,
                            (short) 0,
                            abData,
                            (short) 0,
                            (short) RESPONSE_GET_SPECIFIC_OTHERS.length);
                }
            } else if (sMode == TAG_CMD_GET_NEXT) {
                ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
            } else if (sMode == TAG_CMD_GET_REFRESH) {
                sOutLength = Util.arrayCopyNonAtomic(RESPONSE_GET_REFRESH,
                        (short) 0,
                        abData,
                        (short) 0,
                        (short) RESPONSE_GET_REFRESH.length);
            } else {
                ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
            }
        } catch (ISOException e) {
            sSW1SW2 = e.getReason();
        } catch (Exception e) {
            sSW1SW2 = ISO7816.SW_UNKNOWN;
        }

        if (sSW1SW2 != ISO7816.SW_NO_ERROR) {
            ISOException.throwIt(sSW1SW2);
        }

        if (sOutLength > (short) 0) {
            oAPDU.setOutgoingAndSend((short) 0, sOutLength);
        }
    }
}

