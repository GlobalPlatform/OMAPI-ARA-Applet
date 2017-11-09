/*********************************************************************************
 Copyright 2017 GlobalPlatform, Inc. 

 Licensed under the GlobalPlatform/Apache License, Version 2.0 (the "License"); 
 you may not use this file except in compliance with the License. 
 You may obtain a copy of the License at 

 http://www.globalplatform.org/___________ 

 Unless required by applicable law or agreed to in writing, software 
 distributed under the License is distributed on an "AS IS" BASIS, 
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 implied. 
 See the License for the specific language governing permissions and 
 limitations under the License. 
*********************************************************************************/

/*********************************************************************************
 *   CHANGE HISTORY:
 *
 * 2017/11/08:
 * 	- First version.
 *
 ********************************************************************************/

package org.globalplatform.test.omapitest1;

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
