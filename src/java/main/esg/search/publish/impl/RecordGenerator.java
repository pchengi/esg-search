/*******************************************************************************
 * Copyright (c) 2010 Earth System Grid Federation
 * ALL RIGHTS RESERVED. 
 * U.S. Government sponsorship acknowledged.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package esg.search.publish.impl;

import java.util.Arrays;
import java.util.List;

import esg.search.core.Record;
import esg.search.core.RecordImpl;


/**
 * Class to generate a given number of simulated search records.
 */
public class RecordGenerator extends RecordProducerImpl {
	
	private static final List<String> projects = Arrays.asList( new String[] {"AIRS", "MLS", "IPCC5"} );
	private static final List<String> models = Arrays.asList( new String[] {"CCSM", "PCM", "BCM"} );
	private static final List<String> experiments = Arrays.asList( new String[] {"commit", "amip", "picntrl"} );
	private static final List<String> frequencies = Arrays.asList( new String[] {"Monthly", "Daily", "Hourly"} );
	private static final List<String> realms = Arrays.asList( new String[] {"Ocean", "Atmosphere", "Land", "Sea Ice"} );
	private static final List<String> variables = Arrays.asList( new String[] {
			"Downwelling Longwave Radiance in Air",
			"Ocean Integral of Sea Water Temperature wrt Depth"
			}
		);
	
	/**
	 * The number of records to generate, with default value;
	 */
	private int numRecords = 10;
		
	/**
	 * Method that starts generating the pre-configured number of records
	 * and sends them to the subscribed record consumers.
	 * @param numRecords
	 * @throws Exception
	 */
	public void generate() throws Exception {
		
		int iProject = -1;
		int iModel = -1;
		int iExperiment = -1;
		int iFrequency = -1;
		int iRealm = -1;
		int iVariable = -1;
		for (int iRecord=0; iRecord<numRecords; iRecord++) {
			
			final Record record = new RecordImpl(Integer.toString(iRecord));
			record.addField("title", "Record #"+iRecord);
			record.addField("name", "Record #"+iRecord);
			record.addField("description", "This is the record #"+iRecord+" long description");
			record.addField("type", "Dataset");
			record.addField("url", "http://mysite.com/records/id="+iRecord);
						
			iProject = selectNextIndex(projects, iProject);
			record.addField("project", projects.get(iProject));
			
			iModel = selectNextIndex(models, iModel);
			record.addField("model", models.get(iModel));
			
			iExperiment = selectNextIndex(experiments, iExperiment);
			record.addField("experiment", experiments.get(iExperiment));
			
			iFrequency = selectNextIndex(frequencies, iFrequency);
			record.addField("frequency", frequencies.get(iFrequency));
			
			iRealm = selectNextIndex(realms, iRealm);
			record.addField("realm", realms.get(iRealm));
			
			iVariable = selectNextIndex(variables, iVariable);
			record.addField("variable", variables.get(iVariable));
			
			// notify subscribed consumers
			notify(record);
						
		}
		
	}
	
	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
	}

	/**
	 * Utility method to cycle through a list endlessly.
	 * @param instances
	 * @param i
	 * @return
	 */
	private static int selectNextIndex(final List<String> values, int i) {
		if (i+1<values.size()) {
			return i+1;
		} else {
			return 0;
		}
	}


}
