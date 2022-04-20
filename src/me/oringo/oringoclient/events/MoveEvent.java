// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class MoveEvent extends Event
{
    public double x;
    public double y;
    public double z;
    
    public MoveEvent(final double x, final double y, final double z) {
        this.x = x;
        this.z = z;
        this.y = y;
    }
}
