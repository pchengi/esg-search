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
package esg.search.harvest.oai;

import java.net.URI;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import esg.search.core.Record;
import esg.search.harvest.impl.InMemoryStore;
import esg.search.harvest.xml.dif.MetadataHandlerDifImpl;

/**
 * Test class for {@link OaiHarvester}.
 *
 */
public class OaiHarvesterTest {
	
	private final static ClassPathResource XMLFILE = new ClassPathResource("esg/search/harvest/oai/oai_dif.xml");
	
	OaiHarvester oaiHarvester;
	InMemoryStore consumer;
	
	@Before
	public void setup() {
		oaiHarvester = new OaiHarvester( new MetadataHandlerDifImpl() );
		consumer = new InMemoryStore();
		oaiHarvester.subscribe(consumer);
		
	}
	
	/**
	 * Tests crawling of a OAI/DIF XML document (as serialized to the file system).
	 * @throws Exception
	 */
	@Test
	public void crawl() throws Exception {
		
		final URI uri = new URI( "file://"+XMLFILE.getFile().getAbsolutePath() );
		oaiHarvester.crawl(uri, true);
		
		// tests number of metadata records
		// note: "deleted" records are ignored
		final List<Record> records = consumer.getRecords();
		Assert.assertTrue(records.size()==2);
		
		
	}

}
