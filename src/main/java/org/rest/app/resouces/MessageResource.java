package org.rest.app.resouces;


import org.rest.app.model.Comment;
import org.rest.app.model.Message;
import org.rest.app.resources.beans.MessageFilterBean;
import org.rest.app.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/messages")
@Produces( value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_XML)
public class MessageResource {

    MessageService messageService = new MessageService();
/*
************************************Antes de agregar los parametros en una clase aparte*********************************************
    @GET
    public List<Message> getMessages(@QueryParam("year") int year, @QueryParam("start") int start, @QueryParam("size") int size) {

        if (year > 0)
            return messageService.getAllMessagesForYear(year);
        if (start >=0 && size >0)
            return  messageService.getAllMessagePaginated(start,size);

        return messageService.getAllMessages();
    }
    ***********************************************************************************************************************************
    */

    @GET
    public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {

        if (filterBean.getYear() > 0)
            return messageService.getAllMessagesForYear(filterBean.getYear());
        if (filterBean.getStart() >=0 && filterBean.getSize() >0)
            return  messageService.getAllMessagePaginated(filterBean.getStart(),filterBean.getSize());

        return messageService.getAllMessages();
    }



    @GET
    @Path("/{messageId}")
    public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
   // public Message getMessage(@PathParam("messageId") long id) {
        Message message = messageService.getMessage(id);
        message.addLink(getUriForSelf(uriInfo, message), "self");
        message.addLink(getUriForProfile(uriInfo, message), "profile");
        message.addLink(getUriForComments(uriInfo, message), "comments");
        return message;
    }

    private String getUriForSelf(UriInfo uriInfo, Message message) {
        String uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(Long.toString(message.getId()))
                .build()
                .toString();
        return uri;
    }
    private String getUriForComments(UriInfo uriInfo, Message message) {
        URI uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "getCommentResource")
                .path(CommentResource.class)
                .resolveTemplate("messageId", message.getId())
                .build();
        return uri.toString();
    }

    private String getUriForProfile(UriInfo uriInfo, Message message) {
        URI uri = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(message.getAuthor())
                .build();
        return uri.toString();
    }

    @DELETE
    @Path("/{messageId}")
    public Message deleteMessage(@PathParam("messageId") long id) {
        return messageService.removeMessage(id);
    }

    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long id, Message message) {
        message.setId(id);
        return messageService.updateMessage(message);
    }

    @POST
    public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
    //public Response addMessage(Message message) {
        Message newMessage = messageService.addMessage(message);
        String newId = String.valueOf(newMessage.getId());
        URI uri  = uriInfo.getAbsolutePathBuilder().path(newId).build();

        return  Response.created(uri).entity(newMessage).build();
        //return Response.created(new URI("/webapi/messages/" + newMessage.getId())).entity(newMessage).build();
        //return Response.status(Response.Status.CREATED).entity(newMessage).build();
        //return messageService.addMessage(message);
    }


    @Path("/{messageId}/comments")
    public CommentResource getCommentResource()
    {

        return new CommentResource();
    }

}
