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
package esg.search.harvest.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import esg.search.harvest.api.HarvestingService;
import esg.search.harvest.api.MetadataRepositoryCrawler;
import esg.search.harvest.api.MetadataRepositoryType;
import esg.search.harvest.api.RecordConsumer;
import esg.search.harvest.api.RecordProducer;

/**
 * Service class that manages the harvesting of search records from different remote metadata repositories.
 */
public class HarvestingServiceImpl implements HarvestingService {
	
	final Map<MetadataRepositoryType, MetadataHarvester> harvesters;
	
	final List<RecordConsumer> consumers;
	
	private static final Log LOG = LogFactory.getLog(HarvestingServiceImpl.class);
	
	public HarvestingServiceImpl(final Map<MetadataRepositoryType, MetadataHarvester> harvesters, final List<RecordConsumer> consumers) {
		
		this.harvesters = harvesters;
		this.consumers = consumers;
		
		// subscribe record consumers to record producers
		for (final RecordProducer producer : harvesters.values()) {
			for (final RecordConsumer consumer : consumers) {
				producer.subscribe(consumer);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see esg.search.harvest.HarvestingService#harvest(java.net.URI, boolean, esg.search.harvest.MetadataRepositoryType)
	 */
	public void harvest(final String uri, boolean recursive, final MetadataRepositoryType metadataRepositoryType) throws Exception {
		
		LOG.info("uri="+uri+" recursive="+recursive+" metadataRepositoryType="+metadataRepositoryType);
		MetadataRepositoryCrawler crawler = harvesters.get(metadataRepositoryType);
		Assert.notNull(crawler, "Unsupported MetadataRepositoryType:"+metadataRepositoryType);
		crawler.crawl(new URI(uri), recursive);
		
	}

}
