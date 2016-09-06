/*
 * JCudaUtils - Utilities for JCuda 
 * 
 * Copyright (c) 2010-2016 Marco Hutter - http://www.jcuda.org
 */
package jcuda.utils.test;

import static jcuda.driver.JCudaDriver.*;

import java.util.Arrays;

import jcuda.*;
import jcuda.driver.*;
import jcuda.utils.KernelLauncher;

/**
 * A sample demonstrating how the KernelLauncher class may
 * be used to compile inlined source code and execute a
 * kernel function from the source code.
 */
public class KernelLauncherSample
{
    /**
     * Entry point of the sample
     * 
     * @param args Not used
     */
    public static void main(String args[])
    {
        JCudaDriver.setExceptionsEnabled(true);

        String sourceCode =  
            "extern \"C\"" + "\n" +
            "__global__ void add(float *result, float *a, float *b)" + "\n" +
            "{" + "\n" +
            "    int i = threadIdx.x;" + "\n" +
            "    result[i] = a[i] + b[i];" + "\n" +
            "}";
        
        // Prepare the kernel
        System.out.println("Preparing the KernelLauncher...");
        KernelLauncher kernelLauncher = 
            KernelLauncher.compile(sourceCode, "add");
        
        // Create the input data
        System.out.println("Creating input data...");
        int size = 10;
        float result[] = new float[size];
        float a[] = new float[size];
        float b[] = new float[size];
        for (int i=0; i<size; i++)
        {
            a[i] = i;
            b[i] = i;
        }
        
        // Allocate the device memory and copy the input
        // data to the device
        System.out.println("Initializing device memory...");
        CUdeviceptr dResult = new CUdeviceptr();
        cuMemAlloc(dResult, size * Sizeof.FLOAT);
        CUdeviceptr dA = new CUdeviceptr();
        cuMemAlloc(dA, size * Sizeof.FLOAT);
        cuMemcpyHtoD(dA, Pointer.to(a), size * Sizeof.FLOAT);
        CUdeviceptr dB = new CUdeviceptr();
        cuMemAlloc(dB, size * Sizeof.FLOAT);
        cuMemcpyHtoD(dB, Pointer.to(b), size * Sizeof.FLOAT);
        
        // Call the kernel
        System.out.println("Calling the kernel...");
        kernelLauncher.setBlockSize(size, 1, 1);
        kernelLauncher.call(dResult, dA, dB);

        // Copy the result from the device to the host
        System.out.println("Obtaining results...");
        cuMemcpyDtoH(Pointer.to(result), dResult, size * Sizeof.FLOAT);
        System.out.println("Result: "+Arrays.toString(result));
        
        // Clean up
        cuMemFree(dA);
        cuMemFree(dB);
        cuMemFree(dResult);
    }
}

