/*
 * Copyright (c) 2024, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.svm.core.layeredimagesingleton;

import org.graalvm.nativeimage.ImageSingletons;

import com.oracle.svm.core.imagelayer.ImageLayerBuildingSupport;
import com.oracle.svm.core.util.VMError;

public interface InitialLayerOnlyImageSingleton extends LayeredImageSingleton {

    @Override
    default PersistFlags preparePersist(ImageSingletonWriter writer) {
        VMError.guarantee(ImageLayerBuildingSupport.buildingInitialLayer(), "This singleton should only be installed in the initial layer");
        return PersistFlags.FORBIDDEN;
    }

    /**
     * When true is returned, runtime code within future layers can call
     * {@link ImageSingletons#lookup} and the singleton will be constant folded into the code. In
     * the future layers it will still not be possible to call {@link ImageSingletons#lookup} at
     * build time.
     */
    default boolean accessibleInFutureLayers() {
        return false;
    }
}
