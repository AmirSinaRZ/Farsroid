(function($, window, document, undefined) {
    var pluginName = "wzAnimationPlay";
    var WzAnimationPlay = function() {
        function WzAnimationPlay(element, options) {
            if (options === void 0) {
                options = {};
            }
            this.defaults = {};
            this.name = pluginName;
            this.duration = 600;
            this.delay = 0;
            this.easing = {
                linear: function(t) {
                    return t;
                },
                easeInQuad: function(t) {
                    return t * t;
                },
                easeOutQuad: function(t) {
                    return t * (2 - t);
                },
                easeInOutQuad: function(t) {
                    return t < .5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
                },
                easeInCubic: function(t) {
                    return t * t * t;
                },
                easeOutCubic: function(t) {
                    return --t * t * t + 1;
                },
                easeInOutCubic: function(t) {
                    return t < .5 ? 4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1;
                },
                easeInQuart: function(t) {
                    return t * t * t * t;
                },
                easeOutQuart: function(t) {
                    return 1 - --t * t * t * t;
                },
                easeInOutQuart: function(t) {
                    return t < .5 ? 8 * t * t * t * t : 1 - 8 * --t * t * t * t;
                },
                easeInQuint: function(t) {
                    return t * t * t * t * t;
                },
                easeOutQuint: function(t) {
                    return 1 + --t * t * t * t * t;
                },
                easeInOutQuint: function(t) {
                    return t < .5 ? 16 * t * t * t * t * t : 1 + 16 * --t * t * t * t * t;
                }
            };
            this.element = element;
            this.$element = $(element);
            this.$mainElement = this.$element;
            this.prefix = options.prefix || "data-animation";
            if (!this.$element.attr(this.prefix)) {
                this.$mainElement = this.$element.parents("[" + this.prefix + "]").first();
            }
            this.options = $.extend({}, this.defaults, options);
            this.init();
            if (typeof this[this.animation] === "function") {
                this.elementInfo = this.getElementInfo();
                this.$element.addClass("wz-animation-play").attr(this.prefix + "-percent", 0);
                this[this.animation](0, this.elementInfo);
            }
        }
        WzAnimationPlay.prototype.init = function() {
            var $elementData = this.$mainElement;
            this.animation = this.camelize($elementData.attr(this.prefix) || "none") || "none";
            this.duration = $elementData.attr(this.prefix + "-duration") * 1e3 || 1200;
            this.distance = $elementData.attr(this.prefix + "-distance") || 150;
            this.directionAngle = $elementData.attr(this.prefix + "-direction-angle") || 0;
            this.delay = $elementData.attr(this.prefix + "-delay") * 1e3 || 0;
            this.direction = $elementData.attr(this.prefix + "-direction") || "from_center";
            this.power = $elementData.attr(this.prefix + "-power") || "soft";
            this.spins = $elementData.attr(this.prefix + "-spins") || 1;
            this.count = this.$element.attr("data-animation-count") || "100";
            this.speed = $elementData.attr("data-animation-speed") || "slow";
        };
        WzAnimationPlay.prototype.changeOption = function(values) {
            var _this = this;
            $.each(values, function(index, value) {
                _this[index] = value;
            });
        };
        WzAnimationPlay.prototype.switchDirection = function() {
            switch (this.direction) {
              case "from_top":
                this.directionAngle = 0;
                break;

              case "from_top_right":
                this.directionAngle = 45;
                break;

              case "from_right":
                this.directionAngle = 90;
                break;

              case "from_bottom_right":
                this.directionAngle = 135;
                break;

              case "from_bottom":
                this.directionAngle = 180;
                break;

              case "from_bottom_left":
                this.directionAngle = 225;
                break;

              case "from_left":
                this.directionAngle = 270;
                break;

              case "from_top_left":
                this.directionAngle = 315;
                break;
            }
        };
        WzAnimationPlay.prototype.getXY = function(percent) {
            var y = Math.sin(this.toRadians(this.directionAngle)) * this.distance * (1 - percent), x = -(Math.cos(this.toRadians(this.directionAngle)) * this.distance) * (1 - percent);
            return {
                y: y,
                x: x
            };
        };
        WzAnimationPlay.prototype.getElementInfo = function() {
            var $window = $(window);
            var screenWidth = $window.width();
            var screenHeight = $window.height();
            var elementWidth = this.$element.width();
            var elementHeight = this.$element.height();
            var elementLeft = this.$element.offset().left;
            var elementTop = this.$element.offset().top;
            var elementPositionLeft = this.$mainElement.position().left;
            var elementPositionTop = this.$mainElement.position().top;
            var $parent;
            var parentTop;
            var parentLeft;
            if (this.$mainElement.parents(".wz-element").first().length > 0) {
                $parent = this.$mainElement.parents(".wz-element").first();
                parentLeft = $parent.offset().left;
                parentTop = $parent.offset().left;
            }
            return {
                screenHeight: screenHeight,
                screenWidth: screenWidth,
                elementWidth: elementWidth,
                elementHeight: elementHeight,
                elementLeft: elementLeft,
                elementTop: elementTop,
                elementPositionLeft: elementPositionLeft,
                elementPositionTop: elementPositionTop,
                $parent: $parent,
                parentTop: parentTop,
                parentLeft: parentLeft
            };
        };
        WzAnimationPlay.prototype.play = function() {
            var _this = this;
            setTimeout(function() {
                var info = _this.elementInfo;
                var start = function() {
                    _this.$element.addClass("wz-animation-play").animate({
                        now: "+=1"
                    }, {
                        duration: Number(_this.duration),
                        step: function(now) {
                            var percent = 1 - Math.abs(now - Math.ceil(now));
                            if (now === 0) {
                                percent = 0;
                            } else if (now === 1) {
                                percent = 1;
                            }
                            if (typeof _this[_this.animation] === "function") {
                                _this.$element.attr(_this.prefix + "-percent", percent * 100);
                                _this[_this.animation](percent, info);
                            }
                        },
                        complete: function() {}
                    });
                };
                start();
            }, this.delay);
        };
        WzAnimationPlay.prototype.goStep = function(percent, speed) {
            if (speed === void 0) {
                speed = 0;
            }
            if (percent < 0) {
                percent = 0;
            } else if (percent > 100) {
                percent = 100;
            }
            if (speed !== 0) {
                percent /= 100;
                var easing = this.easing;
                if (speed == 4) {
                    percent = easing.easeOutQuint(percent);
                } else if (speed == 3) {
                    percent = easing.easeOutQuart(percent);
                } else if (speed == 2) {
                    percent = easing.easeOutCubic(percent);
                } else if (speed == 1) {
                    percent = easing.easeOutQuad(percent);
                } else if (speed == -4) {
                    percent = easing.easeInQuint(percent);
                } else if (speed == -3) {
                    percent = easing.easeInQuart(percent);
                } else if (speed == -2) {
                    percent = easing.easeInCubic(percent);
                } else if (speed == -1) {
                    percent = easing.easeInQuad(percent);
                }
                percent *= 100;
            }
            if (typeof this[this.animation] === "function") {
                this.$element.addClass("wz-animation-play").attr(this.prefix + "-percent", percent);
                this[this.animation](percent / 100, this.getElementInfo());
            }
        };
        WzAnimationPlay.prototype.glideIn = function(percent) {
            var x = Math.sin(this.toRadians(this.directionAngle)) * this.distance * (1 - percent), y = -(Math.cos(this.toRadians(this.directionAngle)) * this.distance) * (1 - percent);
            this.$element.css({
                transform: "translate3d(" + x + "px, " + y + "px, 0px)"
            });
        };
        WzAnimationPlay.prototype.bounceIn = function(percent) {
            var halfWidth = this.$element.outerWidth() / 2, halfHeight = this.$element.outerHeight() / 2, timing = function(initialRange) {
                return Math.pow(2, 10 * (1 - percent - 1)) * Math.cos(20 * Math.PI * initialRange / 3 * (1 - percent));
            }, x, y, scaleX, range, scaleY;
            if (this.power === "soft") {
                range = .6;
            } else if (this.power === "medium") {
                range = .9;
            } else if (this.power === "hard") {
                range = 1.2;
            }
            var rate = 1 - this.easing.easeOutQuad(timing(range));
            if (this.direction === "from_center") {
                x = 0;
                y = 0;
            } else if (this.direction === "from_top_left") {
                x = halfWidth * rate - halfWidth;
                y = halfHeight * rate - halfHeight;
            } else if (this.direction === "from_top_right") {
                x = -halfWidth * rate + halfWidth;
                y = halfHeight * rate - halfHeight;
            } else if (this.direction === "from_bottom_right") {
                x = halfWidth * (1 - rate * rate);
                y = halfHeight * (1 - rate * rate);
            } else if (this.direction === "from_bottom_left") {
                x = -halfWidth * (1 - rate * rate);
                y = halfHeight * (1 - rate * rate);
            }
            scaleX = rate;
            scaleY = rate;
            this.$element.css({
                opacity: 1 - this.easing.easeOutQuad(timing(1.2)),
                transform: "translate3d(" + x + "px, " + y + "px, 0px) scale(" + scaleX + ", " + scaleY + ")"
            });
        };
        WzAnimationPlay.prototype.fadeIn = function(percent) {
            this.$element.css({
                opacity: this.easing.easeInOutCubic(percent)
            });
        };
        WzAnimationPlay.prototype.floatIn = function(percent) {
            var initDistance;
            if (this.direction === "from_left" || this.direction === "from_right") {
                initDistance = this.$element.outerWidth() / 2;
            } else if (this.direction === "from_top" || this.direction === "from_bottom") {
                initDistance = this.$element.outerHeight();
            }
            this.switchDirection();
            var y = Math.sin(this.toRadians(this.directionAngle)) * initDistance * (1 - percent), x = -(Math.cos(this.toRadians(this.directionAngle)) * initDistance) * (1 - percent);
            this.$element.css({
                transform: "translate3d(" + y + "px, " + x + "px, 0px)",
                opacity: percent
            });
        };
        WzAnimationPlay.prototype.expandIn = function(percent) {
            var scale, power;
            if (this.power === "soft") {
                power = .9;
            }
            if (this.power === "medium") {
                power = .4;
            }
            if (this.power === "hard") {
                power = 0;
            }
            scale = power + percent * (1 - power);
            this.$element.css({
                transform: "scale(" + scale + ")",
                opacity: percent
            });
        };
        WzAnimationPlay.prototype.spinIn = function(percent) {
            var rotate, scale, power, easingPercent = this.easing.easeInOutCubic(percent), spins = Math.round(this.spins);
            if (this.direction === "counter_clockwise") {
                rotate = easingPercent * -360 * spins;
            } else {
                rotate = easingPercent * 360 * spins;
            }
            if (this.power === "soft") {
                power = 1;
            }
            if (this.power === "medium") {
                power = .6;
            }
            if (this.power === "hard") {
                power = 0;
            }
            scale = power + easingPercent * (1 - power);
            this.$element.css({
                transform: "rotate( " + rotate + "deg) scale(" + scale + ")",
                opacity: easingPercent
            });
        };
        WzAnimationPlay.prototype.flyIn = function(percent) {
            this.distance = $(window).width();
            this.switchDirection();
            var timing = this.easing.easeInOutCubic(percent), x = this.getXY(timing).x, y = this.getXY(timing).y;
            this.$element.css({
                transform: "translate3d(" + y + "px, " + x + "px, 0px)",
                opacity: timing
            });
        };
        WzAnimationPlay.prototype.turnIn = function(percent, params) {
            var easingPercent = this.easing.easeInCubic(percent), a = percent, b = 1 - percent, c = params.screenWidth - params.elementWidth - params.elementLeft, d = params.elementTop - params.elementHeight / 2;
            if (params.$parent) {
                c = params.parentLeft + params.elementPositionLeft + params.elementWidth - params.$parent.width();
                d = params.elementPositionTop - params.elementHeight / 2;
            }
            c = c - c * percent;
            d = -d + d * percent;
            if (this.direction === "from_left") {
                b = -b;
                c = -c;
            }
            this.$element.css({
                opacity: this.easing.easeInOutCubic(percent),
                transform: "matrix3d(" + a + ", " + b + ", 0, 0, " + -b + ", " + a + ", 0, 0, 0, 0, 1, 0, " + c + ", " + d + ", 0, 1)"
            });
        };
        WzAnimationPlay.prototype.arcIn = function(percent) {
            var a = percent - (1 - percent), b = percent - percent * percent, c = -(a / 200), distancePercent = this.distance * percent, d = b * distancePercent * 5, e = (percent - percent * percent) / 50, f = -390 * (1 - percent * percent), g = 3 * (1 - 1 * percent) + percent;
            if (this.direction === "from_left") {
                e = -e;
                d = -d;
            }
            this.$element.css({
                opacity: percent,
                transform: "matrix3d(" + a + ", 0, " + b + ", " + e + ", 0, 1, 0, 0, " + b + ", 0, " + a + ", " + c + ", " + d + ", 0, " + f + ", " + g + ")"
            });
        };
        WzAnimationPlay.prototype.puffIn = function(percent) {
            var formOneToZero = 1 - percent * percent, start, x;
            if (this.power === "soft") start = .2;
            if (this.power === "medium") start = 1;
            if (this.power === "hard") start = 3;
            x = start * formOneToZero + 1;
            this.$element.css({
                opacity: percent,
                transform: "translate3d(0, 0, 0) scale(" + x + ")"
            });
        };
        WzAnimationPlay.prototype.foldIn = function(percent) {
            var width = this.$element.width(), height = this.$element.height(), formOneToZero = 1 - percent * percent, origin, a, b, c, d, e, f, g, h, i, j;
            if (this.direction === "from_bottom" || this.direction === "from_top") {
                a = 1;
                b = c = g = 0;
                d = percent;
                j = percent * -.00125;
                if (this.direction === "from_bottom") {
                    e = 1 - percent;
                    f = formOneToZero * -.00125;
                    origin = width / 2 + "px " + height + "px 0px";
                }
                if (this.direction === "from_top") {
                    e = percent - 1;
                    f = formOneToZero * .00125;
                    origin = width / 2 + "px 0px 0px";
                }
                i = d;
                h = -e;
            } else if (this.direction === "from_right" || this.direction === "from_left") {
                d = 1;
                e = f = h = 0;
                a = percent;
                j = percent * -.00125;
                if (this.direction === "from_right") {
                    b = formOneToZero;
                    c = formOneToZero * -.00125;
                    origin = width + "px " + height + "px 0px";
                }
                if (this.direction === "from_left") {
                    b = -formOneToZero;
                    c = formOneToZero * .00125;
                    origin = "0px " + height + "px 0px";
                }
                i = a;
                g = -b;
            }
            this.$element.css({
                opacity: this.easing.easeOutCubic(percent),
                transform: "matrix3d(" + a + ", 0, " + b + ", " + c + ", 0, " + d + ", " + e + ", " + f + ", " + g + ", " + h + ", " + i + ", " + j + ", 0, 0, 0, 1)",
                "transform-origin": origin
            });
        };
        WzAnimationPlay.prototype.flipIn = function(percent) {
            var easing = this.easing.easeInCubic(percent), formOneToZero = 1 - easing * easing, a, b, c, d, e, f, g, h, i, j;
            if (this.direction === "from_bottom" || this.direction === "from_top") {
                a = 1;
                b = c = g = 0;
                d = easing;
                j = -.00125 * easing;
                if (this.direction === "from_bottom") {
                    e = easing - 1;
                    f = formOneToZero * .00125;
                }
                if (this.direction === "from_top") {
                    e = 1 - easing;
                    f = formOneToZero * -.00125;
                }
                i = d;
                h = -e;
            }
            if (this.direction === "from_right" || this.direction === "from_left") {
                d = 1;
                e = f = h = 0;
                a = easing;
                j = easing * -.00125;
                if (this.direction === "from_right") {
                    b = -formOneToZero;
                    c = formOneToZero * .00125;
                }
                if (this.direction === "from_left") {
                    b = formOneToZero;
                    c = formOneToZero * -.00125;
                }
                i = a;
                g = -b;
            }
            this.$element.css({
                opacity: this.easing.easeOutCubic(percent),
                transform: "matrix3d(" + a + ", 0, " + b + ", " + c + ", 0, " + d + ", " + e + ", " + f + ", " + g + ", " + h + ", " + i + ", " + j + ", 0, 0, 0, 1)"
            });
        };
        WzAnimationPlay.prototype.reveal = function(percent) {
            var easingPercent = percent, frommOneToZero = 1 - easingPercent * easingPercent, polygon, multiplyPercent = easingPercent * 100, from50To100 = 50 + 50 * easingPercent, reversePercent = frommOneToZero * 100;
            if (this.direction === "from_left") {
                polygon = " polygon(0% 0%, " + multiplyPercent + "% 0%, " + multiplyPercent + "% 100%, 0% 100%)";
            }
            if (this.direction === "from_right") {
                polygon = " polygon(" + reversePercent + "% 0%, 100% 0%, 100% 100%, " + reversePercent + "% 100%)";
            }
            if (this.direction === "from_bottom") {
                polygon = "polygon(0% " + reversePercent + "%, 100% " + reversePercent + "%, 100% 100%, 0% 100%)";
            }
            if (this.direction === "from_top") {
                polygon = "polygon(0% 0%, 100% 0%, 100% " + multiplyPercent + "%, 0% " + multiplyPercent + "%)";
            }
            if (this.direction === "from_center") {
                polygon = "polygon(" + reversePercent / 2 + "% " + reversePercent / 2 + "%, " + from50To100 + "% " + reversePercent / 2 + "%, " + from50To100 + "% " + from50To100 + "%, " + reversePercent / 2 + "% " + from50To100 + "%)";
            }
            this.$element.css({
                "webkit-clip-path": polygon
            });
        };
        WzAnimationPlay.prototype.slideIn = function(percent) {
            var easingPercent = this.easing.easeInOutCubic(percent), width = this.$element.width() * easingPercent, height = this.$element.height() * easingPercent, frommOneToZero = 1 - easingPercent * easingPercent, multiplyPercent = easingPercent * 100, reversePercent = frommOneToZero * 100, polygon, x, y;
            if (this.direction === "from_left") {
                polygon = "polygon(0% 0%, " + multiplyPercent + "% 0%, " + multiplyPercent + "% 100%,0% 100%)";
                x = frommOneToZero * -width;
                y = 0;
            }
            if (this.direction === "from_right") {
                polygon = "polygon(" + reversePercent + "% 0%, 100% 0%, 100% 100%, " + reversePercent + "% 100%)";
                x = frommOneToZero * width;
                y = 0;
            }
            if (this.direction === "from_top") {
                polygon = "polygon(0% " + reversePercent + "%, 100% " + reversePercent + "%, 100% 100%, 0% 100%)";
                x = 0;
                y = frommOneToZero * -height;
            }
            if (this.direction === "from_bottom") {
                polygon = "polygon(0% 0%, 100% 0%, 100% " + multiplyPercent + "%, 0% " + multiplyPercent + "%)";
                x = 0;
                y = frommOneToZero * height;
            }
            this.$element.css({
                opacity: this.easing.easeOutCubic(percent),
                "clip-path": polygon,
                transform: "translate3d(" + x + "px, " + y + "px, 0px)"
            });
        };
        WzAnimationPlay.prototype.counter = function(percent) {
            if (this.speed === "slow") {
                this.duration = 5e3;
            } else if (this.speed === "medium") {
                this.duration = 3e3;
            } else if (this.speed === "fast") {
                this.duration = 1e3;
            }
            this.$element.html("" + Math.floor(+this.count * (1 - Math.sin(Math.acos(percent)))));
        };
        WzAnimationPlay.prototype.toRadians = function(angle) {
            return angle * (Math.PI / 180);
        };
        WzAnimationPlay.prototype.camelize = function(str) {
            return str.replace("wz-", "").replace(/(?:^\w|[A-Z]|\b\w)/g, function(word, index) {
                return index == 0 ? word.toLowerCase() : word.toUpperCase();
            }).replace(/\s+/g, "").replace("-", "");
        };
        return WzAnimationPlay;
    }();
    $.fn[pluginName] = function(method) {
        var options = [];
        for (var _i = 1; _i < arguments.length; _i++) {
            options[_i - 1] = arguments[_i];
        }
        return this.each(function() {
            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" + pluginName, new WzAnimationPlay(this, method));
            } else if (typeof method === "string") {
                var plugin = $.data(this, "plugin_" + pluginName);
                plugin[method].apply(plugin, options);
            }
        });
    };
})(jQuery, window, document);
