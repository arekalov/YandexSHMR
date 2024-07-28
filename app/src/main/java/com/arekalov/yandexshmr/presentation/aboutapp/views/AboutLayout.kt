fun getJson(theme: String, isAppLiked: Boolean): String {
    return """
  {
    "card": {
      "log_id": "div2_sample_card",
      "states": [
        {
          "state_id": 0,
          "div": {
            "items": [
              {
                "type": "container",
                "alignment_horizontal": "left",
                "height": {
                  "type": "fixed",
                  "value": 48
                },
                "width": {
                  "type": "fixed",
                  "value": 48
                },
                "items": [
                  {
                    "type": "_template_close",
                    "alignment_horizontal": "left",
                    "height": {
                      "type": "fixed",
                      "value": 18
                    },
                    "width": {
                      "type": "fixed",
                      "value": 18
                    },
                    "tint_color": "@{getDictOptColor('#00ffffff', local_palette, 'color2', theme)}",
                    "margins": {
                      "top": 15,
                      "left": 15
                    }
                  }
                ],
                "margins": {
                  "top": 50,
                  "left": 20
                }
              },
              {
                "type": "container",
                "width": {
                  "type": "match_parent"
                },
                "height": {
                  "type": "fixed",
                  "value": 155
                },
                "alignment_horizontal": "center",
                "items": [
                  {
                    "type": "text",
                    "text": "ToDoList - приложение для удобного создания дел.",
                    "width": {
                      "type": "match_parent"
                    },
                    "height": {
                      "type": "wrap_content"
                    },
                    "font_size": 16,
                    "text_color": "@{getDictOptColor('#00ffffff', local_palette, 'color2', theme)}"
                  },
                  {
                    "type": "separator",
                    "width": {
                      "type": "match_parent"
                    },
                    "height": {
                      "type": "fixed",
                      "value": 15
                    }
                  },
                  {
                    "type": "text",
                    "text": "Версия: 1.5.0",
                    "font_size": 16,
                    "width": {
                      "type": "match_parent"
                    },
                    "alignment_horizontal": "left",
                    "alignment_vertical": "top",
                    "height": {
                      "type": "fixed",
                      "value": 21
                    },
                    "text_color": "@{getDictOptColor('#00ffffff', local_palette, 'color2', theme)}"
                  },
                  {
                    "type": "image",
                    "width": {
                      "type": "fixed",
                      "value": 55
                    },
                    "height": {
                      "type": "fixed",
                      "value": 57
                    },
                    "preload_required": true,
                    "image_url": "https://www.svgrepo.com/show/503110/todo.svg",
                    "alignment_horizontal": "center",
                    "alignment_vertical": "top",
                    "tint_color": "#007aff"
                  }
                ],
                "background": [
                  {
                    "type": "solid",
                    "color": "@{getDictOptColor('#00ffffff', local_palette, 'color3', theme)}"
                  }
                ],
                "margins": {
                  "top": 90,
                  "right": 8,
                  "left": 8
                },
                "paddings": {
                  "top": 16,
                  "right": 8,
                  "bottom": 16,
                  "left": 8
                },
                "border": {
                  "has_shadow": true,
                  "shadow": {
                    "offset": {
                      "x": {
                        "value": 1
                      },
                      "y": {
                        "value": 1
                      }
                    }
                  },
                  "corner_radius": 10
                }
              },
              {
                "type": "container",
                "width": {
                  "type": "match_parent"
                },
                "height": {
                  "type": "wrap_content"
                },
                "items": [
                  {
                    "type": "text",
                    "text": "Если вам нравится наше приложение сделайте сердечко красным",
                    "font_size": 16,
                    "width": {
                      "type": "wrap_content",
                      "constrained": true
                    },
                    "alignment_horizontal": "center",
                    "alignment_vertical": "top",
                    "text_color": "@{getDictOptColor('#00ffffff', local_palette, 'color2', theme)}",
                    "text_alignment_horizontal": "center"
                  },
                  {
                    "type": "state",
                    "id": "like_state",
                    "default_state_id": "${if (isAppLiked) "liked" else "disliked"}",
                    "states": [
                      {
                        "state_id": "liked",
                        "div": {
                          "type": "image",
                          "image_url": "https://www.svgrepo.com/show/532473/heart.svg",
                          "width": {
                            "type": "fixed",
                            "value": 30
                          },
                          "tint_color": "#F00",
                          "actions": [
                            {
                              "log_id": "dislike",
                              "url": "div-action://set_state?state_id=0/like_state/disliked"
                            },
                            {
                              "log_id": "change_to_dislike",
                              "url": "my-action://change_like?false"
                            }
                          ],
                          "alignment_horizontal": "center",
                          "alignment_vertical": "top",
                          "paddings": {
                            "top": 8
                          },
                          "accessibility": {
                            "description": "оценить приложение"
                          }
                        }
                      },
                      {
                        "state_id": "disliked",
                        "div": {
                          "type": "image",
                          "image_url": "https://www.svgrepo.com/show/532473/heart.svg",
                          "width": {
                            "type": "fixed",
                            "value": 30
                          },
                          "actions": [
                            {
                              "log_id": "like",
                              "url": "div-action://set_state?state_id=0/like_state/liked"
                            },
                            {
                              "log_id": "change_to_like",
                              "url": "my-action://change_like?true"
                            }
                          ],
                          "alignment_horizontal": "center",
                          "alignment_vertical": "top",
                          "paddings": {
                            "top": 8
                          },
                          "tint_color": "@{getDictOptColor('#00ffffff', local_palette, 'color2', theme)}",
                          "accessibility": {
                            "description": "оценить приложение"
                          }
                        }
                      }
                    ]
                  }
                ],
                "margins": {
                  "top": 280,
                  "right": 8,
                  "left": 8
                },
                "background": [
                  {
                    "type": "solid",
                    "color": "@{getDictOptColor('#00ffffff', local_palette, 'color3', theme)}"
                  }
                ],
                "border": {
                  "has_shadow":true,
                  "shadow": { 
                      "offset": {
                          "x": {
                              "value": 1
                          },
                          "y": {"value": 1}
                      }
                  },
                  "corner_radius": 10
                },
                "paddings": {
                  "top": 8,
                  "right": 8,
                  "bottom": 8,
                  "left": 8
                }
              }
            ],
            "background": [
              {
                "color": "@{getDictOptColor('#00ffffff', local_palette, 'color1', theme)}",
                "type": "solid"
              }
            ],
            "height": {
              "type": "match_parent"
            },
            "orientation": "overlap",
            "type": "container",
            "border": {
              "shadow": {
                "offset": {
                  "x": {
                    "unit": "dp",
                    "value": 10
                  },
                  "y": {
                    "unit": "dp",
                    "value": 10
                  }
                }
              }
            },
            "width": {
              "type": "match_parent"
            }
          }
        }
      ],
      "variables": [
        {
          "type": "dict",
          "name": "local_palette",
          "value": {
            "color0": {
              "name": "primary",
              "light": "#007aff",
              "dark": "#0a84ff"
            },
            "color1": {
              "name": "background",
              "light": "#f7f6f2",
              "dark": "#161618"
            },
            "color2": {
              "name": "onBackground",
              "light": "#000000",
              "dark": "#ffffff"
            },
            "color3": {
              "name": "surface",
              "light": "#fff",
              "dark": "#252528"
            },
            "color4": {
              "name": "shadow",
              "light": "#000000",
              "dark": "#80000000"
            }
          }
        },
        {
          "type": "string",
          "name": "theme",
          "value": "$theme"
        }
      ]
    },
    "templates": {
      "_template_lottie": {
        "type": "gif",
        "scale": "fit",
        "extensions": [
          {
            "id": "lottie",
            "params": {
              "$\lottie_url": "lottie_url"
            }
          }
        ],
        "gif_url": "https://empty"
      },
      "_template_button": {
        "type": "text",
        "content_alignment_horizontal": "center",
        "border": {
          "$\corner_radius": "corners"
        },
        "paddings": {
          "bottom": 24,
          "left": 28,
          "right": 28,
          "top": 22
        },
        "width": {
          "type": "wrap_content"
        }
      },
      "_template_close": {
        "accessibility": {
          "description": "Закрыть",
          "mode": "merge",
          "type": "button"
        },
        "actions": [
          {
            "log_id": "close_popup",
            "url": "my-action://back"
          }
        ],
        "image_url": "https://yastatic.net/s3/home/div/div_fullscreens/cross2.3.png",
        "tint_color": "#73000000",
        "type": "image",
        "preload_required": true
      },
      "_template_list_item": {
        "type": "container",
        "orientation": "horizontal",
        "items": [
          {
            "type": "image",
            "image_url": "https://yastatic.net/s3/home/div/div_fullscreens/hyphen.4.png",
            "$\tint_color": "list_color",
            "width": {
              "type": "fixed",
              "value": 28,
              "unit": 28
            },
            "height": {
              "type": "fixed",
              "value": 28,
              "unit": 28
            },
            "margins": {
              "top": 2,
              "right": 12,
              "bottom": 2
            },
            "paddings": {
              "top": 5,
              "right": 5,
              "left": 5,
              "bottom": 5
            }
          },
          {
            "type": "text",
            "$\text": "list_text",
            "$\text_color": "list_color",
            "font_size": 24,
            "line_height": 32,
            "font_weight": "medium",
            "width": {
              "type": "wrap_content",
              "constrained": true
            }
          }
        ]
      }
    }
  }
""".trimIndent()
}
