/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *     * Redistributions of source code must retain the above 
 *       copyright notice, this list of conditions and the following disclaimer. 
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the documentation 
 *       and/or other materials provided with the distribution. 
 *     * Neither the name of the United States Government nor the 
 *       names of its contributors may be used to endorse or promote products 
 *       derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.hhs.fha.nhinc.adapterdocumentrepository.soap12;

import gov.hhs.fha.nhinc.adapterdocrepository.AdapterDocRepository2Soap12Client;
import gov.hhs.fha.nhinc.document.DocumentConstants;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.xdcommon.XDCommonResponseHelper;
import ihe.iti.xds_b._2007.DocumentRepositoryPortType;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Action;
import javax.xml.ws.BindingType;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This web service class takes a soap 1.1 document retrieve or provide and register document set request and calls a
 * soap 1.2 client to retrieve or store documents from/to a document repository.
 * 
 * @author shawc
 */
public class AdapterDocRepository2Soap12Service implements DocumentRepositoryPortType {
    private static Log log = LogFactory.getLog(AdapterDocRepository2Soap12Service.class);

    /**
     * This method supports the AdapterComponentDocRepository.wsdl for retrieving a document from a document repository
     * for a given soap 1.1 request message.
     * 
     * @param retrieveRequest A RetrieveDocumentSetRequestType object containing the document id and repository id for
     *            the desired document.
     * @return Returns a RetrieveDocumentSetResponseType containing the desired document.
     */

    public RegistryResponseType documentRepositoryProvideAndRegisterDocumentSetB(
            ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType storeRequest) {
        log.trace("Entering AdapterDocRepository2Soap12Service.documentRepositoryProvideAndRegisterDocumentSet() method");

        RegistryResponseType response = null;

        try {
            if (storeRequest != null) {
                log.trace("storeRequest was not null");

                AdapterDocRepository2Soap12Client oClient = new AdapterDocRepository2Soap12Client();

                response = oClient.provideAndRegisterDocumentSet(storeRequest);
            } else {
                String sErrorMessage = "The store document request message was null.";
                log.error(sErrorMessage);
                throw new RuntimeException(sErrorMessage);
            }
        } catch (Exception exp) {
            log.error(exp.getMessage());
            XDCommonResponseHelper helper = new XDCommonResponseHelper();
            response = helper.createError(exp);
            return response;
        }

        log.trace("Leaving AdapterDocRepository2Soap12Service.documentRepositoryProvideAndRegisterDocumentSet() method");
        return response;
    }

    /**
     * This method supports the AdapterComponentDocRepository.wsdl for retrieving a document from a document repository
     * for a given soap request message.
     * 
     * @param retrieveRequest A RetrieveDocumentSetRequestType object containing the document id and repository id for
     *            the desired document.
     * @return Returns a RetrieveDocumentSetResponseType containing the desired document.
     */
    public ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType documentRepositoryRetrieveDocumentSet(
            ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType retrieveRequest) {
        log.trace("Entering AdapterDocRepository2Soap12Service.documentRepositoryRetrieveDocumentSet() method");

        RetrieveDocumentSetResponseType response = null;

        try {
            if (retrieveRequest != null) {
                log.trace("retrieveRequest was not null");

                AdapterDocRepository2Soap12Client oClient = new AdapterDocRepository2Soap12Client();

                response = oClient.retrieveDocument(retrieveRequest);

            } else {
                String sErrorMessage = "The retrieve document request message was null.";
                log.error(sErrorMessage);
                throw new RuntimeException(sErrorMessage);
            }
        } catch (Exception exp) {
            log.error(exp.getMessage());
            response = createErrorResponse(response, exp.getMessage());
        }

        log.trace("Leaving AdapterDocRepository2Soap12Service.documentRepositoryRetrieveDocumentSet() method");
        return response;
    }

    private RetrieveDocumentSetResponseType createErrorResponse(RetrieveDocumentSetResponseType response, String message) {
        response = new RetrieveDocumentSetResponseType();
        XDCommonResponseHelper helper = new XDCommonResponseHelper();
        response.setRegistryResponse(helper.createError(message));
        return response;
    }

}
