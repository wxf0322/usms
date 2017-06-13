import {AnimationEntryMetadata } from '@angular/core';

import { fade } from './fade';
import { bounce } from './bounce';
import { rotate } from './rotate';
import { slide } from './slide';
import { zoom } from './zoom';
import { trigger } from "@angular/animations";

export const animateFactory = (triggerName: string ='animate',duration: string|number = 500,
                               delay: string|number = 0, easing: string = 'ease-in-out'): any => {
    let timing: string = [
        typeof(duration) === 'number' ? `${duration}ms` : duration,
        typeof(delay) === 'number' ? `${delay}ms` : delay,
        easing
    ].join(' ');

    return trigger(triggerName, [
        ...fade(timing),
        ...bounce(timing),
        ...rotate(timing),
        ...slide(timing),
        ...zoom(timing)
    ]);
};
