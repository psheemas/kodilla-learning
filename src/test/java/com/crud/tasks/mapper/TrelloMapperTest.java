package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrelloMapperTest {
    @Test
    public void trelloMapToBoardsTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("test_id","test_board",new ArrayList<>());
        List<TrelloBoardDto> trelloBoardsList = new ArrayList<TrelloBoardDto>();
        trelloBoardsList.add(trelloBoardDto);
        //When
        TrelloBoard trelloBoard = trelloMapper.mapToBoards(trelloBoardsList).get(0);
        //Then
        assertEquals(trelloBoardDto.getId(), trelloBoard.getId());
        assertEquals(trelloBoardDto.getName(), trelloBoard.getName());
        assertEquals(trelloBoardDto.getLists(),trelloBoard.getLists());
    }

    @Test
    public void trelloMapToBoardsDtoTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloBoard trelloBoard = new TrelloBoard("test_id","test_board",new ArrayList<>());
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard);
        //When
        TrelloBoardDto trelloBoardDto = trelloMapper.mapToBoardsDto(trelloBoardList).get(0);
        //Then
        assertEquals(trelloBoard.getId(),trelloBoardDto.getId());
        assertEquals(trelloBoard.getLists(),trelloBoardDto.getLists());
        assertEquals(trelloBoard.getName(),trelloBoardDto.getName());
    }

    @Test
    public void trelloMapToListTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloListDto trelloListDto = new TrelloListDto("test_id","test_list",true);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto);
        //When
        TrelloList trelloList = trelloMapper.mapToList(trelloListDtoList).get(0);
        //Then
        assertEquals(trelloListDto.getId(), trelloList.getId());
        assertEquals(trelloListDto.getName(), trelloList.getName());
        assertEquals(trelloListDto.isClosed(),trelloList.isClosed());
    }

    @Test
    public void trelloMapToListDtoTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloList trelloList = new TrelloList("test_id","test_list",true);
        List<TrelloList> trelloListList = new ArrayList<>();
        trelloListList.add(trelloList);
        //When
        TrelloListDto trelloListDto = trelloMapper.mapToListDto(trelloListList).get(0);
        //Then
        assertEquals(trelloList.getId(),trelloListDto.getId());
        assertEquals(trelloList.getName(),trelloListDto.getName());
        assertEquals(trelloList.isClosed(),trelloListDto.isClosed());

    }

    @Test
    public void trelloMapToBoardTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("test_id","test_list",new ArrayList<>());
        //When
        TrelloBoard trelloBoard = trelloMapper.mapToBoard(trelloBoardDto);
        //Then
        assertEquals(trelloBoardDto.getId(),trelloBoard.getId());
        assertEquals(trelloBoardDto.getName(),trelloBoard.getName());
        assertEquals(trelloBoardDto.getLists(),trelloBoard.getLists());
    }

    @Test
    public void trelloMapToCardDtoTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloCard trelloCard = new TrelloCard("test_id", "test_list","test_pos","test_listId");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(trelloCard.getDescription(),trelloCardDto.getDescription());
        assertEquals(trelloCard.getListId(),trelloCardDto.getListId());
        assertEquals(trelloCard.getName(),trelloCardDto.getName());
        assertEquals(trelloCard.getPos(),trelloCardDto.getPos());
    }

    @Test
    public void trelloMapToCardTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_id", "test_list","test_pos","test_listId");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(trelloCardDto.getDescription(),trelloCard.getDescription());
        assertEquals(trelloCardDto.getListId(),trelloCard.getListId());
        assertEquals(trelloCardDto.getName(),trelloCard.getName());
        assertEquals(trelloCardDto.getPos(),trelloCard.getPos());
    }
}
