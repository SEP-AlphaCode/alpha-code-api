package com.alphacode.alphacodeapi.grpc;

import genplan.Genplan;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenPlanClient {
    private final genplan.GenPlanServiceGrpc.GenPlanServiceBlockingStub blockingStub;

    public GenPlanClient(
            @Value("${python.grpc.host}") String host,
            @Value("${python.grpc.port}") int port
    ) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        this.blockingStub = genplan.GenPlanServiceGrpc.newBlockingStub(channel);
    }

    public Genplan.GeneratePlanResponse generatePlan(String musicName, String musicUrl, double duration) {
        Genplan.GeneratePlanRequest request = Genplan.GeneratePlanRequest.newBuilder()
                .setMusicName(musicName)
                .setMusicUrl(musicUrl)
                .setDuration(duration)
                .build();

        return blockingStub.generatePlan(request);
    }
}

