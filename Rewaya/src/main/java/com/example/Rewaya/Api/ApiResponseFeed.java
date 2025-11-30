package com.example.Rewaya.Api;

import com.example.Rewaya.Model.Novel;
import com.example.Rewaya.Model.Tip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponseFeed
{

    private List<Novel> top3;
    private List<Novel> random3;
    private List<Tip> randomTips;

}
