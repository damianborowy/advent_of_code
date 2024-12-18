using System;
using System.Collections;
using System.Collections.Generic;
using JetBrains.Annotations;
using UnityEngine;

public class Box : MonoBehaviour
{
    public Rigidbody2D rigidBody;
    public Solver solver;
    
    [CanBeNull] public Player player;

    public List<GameObject> GetMovableBoxes(Vector2 direction) => (direction.x, direction.y) switch
    {
        (-1f, 0) or (1f, 0) => GetHorizontalBoxes(direction),
        (0, 1f) or (0, -1f) => GetVerticalBoxes(direction),
        _ => throw new ArgumentOutOfRangeException(nameof(direction), direction, null)
    };

    public List<GameObject> GetHorizontalBoxes(Vector2 direction)
    {
        return new List<GameObject>();
    }
    
    public List<GameObject> GetVerticalBoxes(Vector2 direction) {
        return new List<GameObject>();
    }
    
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (!collision.gameObject.CompareTag("Player") || player is null) return;
        var direction = player.lastPlayerMove;
        var hitObjects = Physics2D.RaycastAll(transform.position, direction);
        
        var movableBoxObjects = new List<GameObject>();
        int? distanceToBox = null;
        foreach (var hit in hitObjects)
        {
            if (!hit.collider.gameObject.CompareTag("Box")) continue;
            
            var currentDistance = Mathf.RoundToInt(hit.distance);

            if (distanceToBox is null || currentDistance == distanceToBox + 1)
            {
                distanceToBox = currentDistance;
                movableBoxObjects.Add(hit.collider.gameObject);
            }
            else break;
        }
        
        movableBoxObjects.ForEach(box => box.transform.position = (Vector2)box.transform.position + direction);
    }
}
