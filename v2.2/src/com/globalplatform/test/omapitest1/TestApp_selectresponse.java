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

package org.simalliance.omapitest1;

import javacard.framework.*;

public class TestApp_selectresponse extends Applet {

	public static void install(byte[] aArray, short sOffset, byte bLength) {
		new TestApp_selectresponse(aArray, sOffset, bLength);
	}

	private TestApp_selectresponse(byte[] aArray, short sOffset, byte bLength) {
		
		register(aArray, (short) (sOffset + 1), aArray[sOffset]);
	}

	public void process(APDU apdu) throws ISOException {

		byte buffer[] = apdu.getBuffer();

 		if (selectingApplet()) {

			short tosend = (short)4;
			if (buffer[ISO7816.OFFSET_P2] != (byte)0x00) {
			   buffer[4] = buffer[ISO7816.OFFSET_P2];
			   tosend = (short)5;
			}
			buffer[0] = (byte) 0xDE;
			buffer[1] = (byte) 0xAD;
			buffer[2] = (byte) 0xC0;
			buffer[3] = (byte) 0xDE;
 			apdu.setOutgoingAndSend((short) 0, tosend);

			return;
		}

		ISOException.throwIt(ISO7816.SW_NO_ERROR);
	}
}
